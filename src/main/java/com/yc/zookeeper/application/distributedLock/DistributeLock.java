/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.zookeeper.application.distributedLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: DistributeLock
 * @Description: zookeeper分布式锁
 * @date 2018/4/1 16:22
 */
public class DistributeLock {

    private static final int SESSION_TIMEOUT = 10000;
    private static final int DEFAULT_TIMEOUT_PERIOD = 10000;
    private static final byte[] data = {0x12,0x34};
    private ZooKeeper zooKeeper;
    private String root;
    private String id;
    private LockNode idName;
    private String ownerId;
    private String lastChildId;
    private Throwable other = null;
    private KeeperException exception = null;
    private InterruptedException interrupt = null;

    public DistributeLock(){}

    public DistributeLock(String root, String hosts) {
        try {
            this.zooKeeper = new ZooKeeper(hosts,SESSION_TIMEOUT,null);
            this.root = root;
            ensureExists(root);
        } catch (IOException e) {
            other = e;
            e.printStackTrace();
        }
    }

    /**
     * 尝试获取锁操作，阻塞式可被中断
     * @throws Exception
     */
    public void lock() throws Exception{
        // 可能初始化的时候就失败了
        if (exception != null){
            throw exception;
        }
        if (interrupt != null){
            throw interrupt;
        }
        if (other != null){
            throw new Exception("其他异常",other);
        }

        // 锁重入
        if (isOwner()){
            return;
        }

        BooleanMutex mutex = new BooleanMutex();
        acquireLock(mutex);
        // 避免zookeeper重启后导致watcher丢失，会出现死锁使用了超时进行重试
        try {
            // 阻塞等待值为true
            mutex.lockTimeout(DEFAULT_TIMEOUT_PERIOD, TimeUnit.MICROSECONDS);
            mutex.lock();
        } catch (Exception e){
            e.printStackTrace();
            if (!mutex.state()){
                lock();
            }
        }

        if (exception != null){
            throw exception;
        }
        if (interrupt != null){
            throw interrupt;
        }
        if (other != null){
            throw new Exception("其他异常",other);
        }
    }

    /**
     * 尝试获得锁对象，不会阻塞
     * @return
     * @throws Exception
     */
    public boolean tryLock() throws Exception{
        // 可能初始化的时候就失败了
        if (exception != null){
            throw exception;
        }
        acquireLock(null);

        if (exception != null){
            throw exception;
        }

        if (interrupt != null){
            Thread.currentThread().interrupt();
        }

        if (other != null){
            throw new Exception("",other);
        }

        return isOwner();
    }

    public void unlock() throws KeeperException {
        if (id != null){
            try {
                zooKeeper.delete(root + "/" + id, -1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException.NoNodeException e){
                e.printStackTrace();
            } finally {
                id = null;
            }
        }
    }

    /**
     * 判断某path节点是否存在，不存在就创建
     * @param path
     */
    private void ensureExists(final String path){
        try {
            Stat stat = zooKeeper.exists(path, false);
            if (stat != null){
                return;
            }
            zooKeeper.create(path,data, ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        } catch (InterruptedException e) {
            interrupt = e;
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (KeeperException e) {
            exception = e;
            e.printStackTrace();
        }

    }


    /**
     * 返回锁对象对应的path
     * @return
     */
    public String getRoot() {
        return root;
    }

    /**
     * 判断当前是不是锁的owner
     * @return
     */
    public boolean isOwner() {
        return id != null && ownerId != null && id.equals(ownerId);
    }

    /**
     * 返回当前的节点id
     * @return
     */
    public String getId() {
        return id;
    }

    private boolean acquireLock(final BooleanMutex mutex){

        try {
            do {
                // 构建当前lock的唯一标识
                if (id == null) {
                    long sessionId = zooKeeper.getSessionId();
                    String prefix = "x-" + sessionId + "-";
                    String path = null;
                    if (null == zooKeeper.exists(root + "/" + prefix, false)) {
                        // 如果第一次，则创建一个节点
                        path = zooKeeper.create(root + "/" + prefix, data,
                                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                    }

                    int index = path.lastIndexOf("/");
                    id = StringUtils.substring(path, index + 1);
                    idName = new LockNode(id);
                }
                if (id != null){
                    List<String> names = zooKeeper.getChildren(root, false);
                    if (names.isEmpty()){
                        //异常情况，重新创建一个
                        id = null;
                    } else {
                        // 对节点排序
                        SortedSet<LockNode> sortedNames = new TreeSet<LockNode>();
                        for (String name : names){
                            sortedNames.add(new LockNode(name));
                        }

                        if (!sortedNames.contains(idName)){
                            // 清空为null，重新创建一个
                            id = null;
                            continue;
                        }

                        // 将第一个节点作为ownerId
                        ownerId = sortedNames.first().getName();
                        if (mutex != null && isOwner()) {
                            // 直接更新状态，返回
                            mutex.unlock();
                            return true;
                        } else if (mutex == null){
                            return isOwner();
                        }

                        SortedSet<LockNode> lessThanMe = sortedNames.headSet(idName);
                        if (!lessThanMe.isEmpty()){
                            // 关注一下排队在自己之前的最近的一个节点
                            LockNode lastChildName = lessThanMe.last();
                            lastChildId = lastChildName.getName();
                            // 异步watcher处理
                            Stat stat = zooKeeper.exists(root + "/" + lastChildId, new Watcher() {
                                public void process(WatchedEvent watchedEvent) {
                                    acquireLock(mutex);
                                }
                            });

                            if (stat == null){
                                // 如果节点不存在，需要自己重新触发一下，watch不会被挂上去
                                acquireLock(mutex);
                            }
                        } else {
                            if (isOwner()){
                                mutex.unlock();
                            } else {
                                // 可能自己的节点已经超时挂了，所以id和ownerId不相同
                                id = null;
                            }
                        }

                    }
                }
            } while (id == null);

        } catch (KeeperException e){
            exception = e;
            if (mutex != null){
                mutex.unlock();
            }
        } catch (InterruptedException e) {
            interrupt = e;
            if (mutex != null){
                mutex.unlock();
            }
        } catch (Throwable e) {
            other = e;
            if (mutex != null){
                mutex.unlock();
            }
        }

        if (isOwner() && mutex != null){
            mutex.unlock();
        }
        return Boolean.FALSE;
    }

}

