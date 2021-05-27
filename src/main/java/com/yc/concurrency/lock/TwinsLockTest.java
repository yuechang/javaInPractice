/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.lock;

import com.yc.concurrency.thread.SleepUtils;
import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * 共享锁示例测试
 *
 * @author Yue Chang
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
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        // 启动10个线程
        for (int i = 0; i < 10; i++){
            Worker worker = new Worker();
            worker.setName("work" + i);
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
/*
成对出现的打印对应的信息，表示每次有两个线程获取到了锁，设置的资源数为2，也可以调节对应的资源数，控制并发

work1
work0

work1
work0

work0
work1

work0
work1

work1
work0

work0
work1

work1
work0

work0
work1

work0
work1

work0
work1



 */
