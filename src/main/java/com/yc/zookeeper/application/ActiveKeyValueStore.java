/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.zookeeper.application;

import com.yc.zookeeper.group.ConnectionWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: ActiveKeyValueStore
 * @Description: 配置服务存入值
 * @date 2018/3/31 11:38
 */
public class ActiveKeyValueStore extends ConnectionWatcher {

    private static final String CHARSET_UTF_8 = "UTF-8";
    private static final int MAX_RETRIES = 10;
    private static final long RETRY_PERIOD_SECONDS = 20;

    public void write(String path, String value) throws KeeperException, InterruptedException, UnsupportedEncodingException {

        int retries = 0;
        while (true){
            try {
                Stat stat = zooKeeper.exists(path, false);
                if (null == stat){
                    zooKeeper.create(path,value.getBytes(CHARSET_UTF_8),
                            ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                } else {
                    zooKeeper.setData(path,value.getBytes(CHARSET_UTF_8),-1);
                }
                return;
            } catch (KeeperException.SessionExpiredException e){
                throw e;
            } catch (KeeperException e){
                if (retries++ == MAX_RETRIES){
                    throw e;
                }
                TimeUnit.SECONDS.sleep(RETRY_PERIOD_SECONDS);
            }
        }
    }

    public String read(String path, Watcher watcher) throws KeeperException, InterruptedException, UnsupportedEncodingException {

        byte[] data = zooKeeper.getData(path, watcher, null);
        return new String(data,CHARSET_UTF_8);
    }
}
