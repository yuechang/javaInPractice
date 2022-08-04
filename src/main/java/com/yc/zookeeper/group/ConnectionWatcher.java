/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.zookeeper.group;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Yue Chang
 * @ClassName: ConnectionWatcher
 * @Description: zookeeper连接超类
 * @date 2018/3/28 22:01
 */
public class ConnectionWatcher implements Watcher{

    public final Integer SESSION_TIMEOUT = 5000;
    // 连接信息量
    private CountDownLatch connectionSignal = new CountDownLatch(1);

    protected ZooKeeper zooKeeper;

    public void connect(String hosts) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(hosts,SESSION_TIMEOUT,this);
        connectionSignal.await();
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected){
            connectionSignal.countDown();
        }
    }
}

