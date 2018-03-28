/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Yue Chang
 * @ClassName: CreateGroup
 * @Description: zookeeper创建组
 * @date 2018/3/28 20:38
 */
public class CreateGroup implements Watcher{

    // 客户端连接服务器session的超时时间
    private static final int SESSION_TIMEOUT = 5000;
    private ZooKeeper zooKeeper;
    // 连接成功信号变量，
    private CountDownLatch connectedSignal = new CountDownLatch(1);

    public void connect(String hosts) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(hosts,SESSION_TIMEOUT,this);
        // 阻塞当前线程，直到zookeeper准备就绪
        connectedSignal.await();
    }

    /**
     *  负责接收Zookeeper数据变化时产生的事件回调
     *  当客户端连接上了zookeeper服务器，Watcher将由process()函数接收一个连接成功的事件
     * @param watchedEvent
     */
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected){
            connectedSignal.countDown();
        }
    }

    public void create(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        String createPath = zooKeeper.create(path,null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println("Created:" + createPath);
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        CreateGroup createGroup = new CreateGroup();
        createGroup.connect(args[0]);
        createGroup.create(args[1]);
        createGroup.close();
    }
}

