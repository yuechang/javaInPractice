/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.lock;

import com.yc.concurrency.thread.SleepUtils;
import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * @author Yue Chang
 * @ClassName: TwinsLockTest
 * @Description: 共享锁示例测试
 * @date 2018/4/29 23:01
 */
public class TwinsLockTest {

    @Test
    public void test() {

        final Lock lock = new TwinsLock();
        class Worker extends Thread {

            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        // 启动10个线程
        for (int i = 0; i < 10; i++){
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.start();
        }
        // 每隔一秒换行
        for (int i = 0; i < 10; i++){
            SleepUtils.second(1);
            System.out.println();
        }
    }
}

