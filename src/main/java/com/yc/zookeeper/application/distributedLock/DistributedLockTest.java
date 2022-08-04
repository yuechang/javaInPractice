/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.zookeeper.application.distributedLock;

import org.apache.zookeeper.KeeperException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Yue Chang
 * @ClassName: DistributedLockTest
 * @Description: 分布式锁测试
 * @date 2018/4/1 21:49
 */
public class DistributedLockTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        final int count = 50;
        final CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++){
            final DistributeLock node = new DistributeLock("/locks", args[0]);
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        // 无阻塞获取锁
                        //boolean lockFlag = node.tryLock();
                        // 阻塞获取锁
                        node.lock();
                        Thread.sleep(100);
                        System.out.println("id: " + node.getId() + " is leader: " + node.isOwner() );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                        try {
                            node.unlock();
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

}

