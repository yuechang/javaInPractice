/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Yue Chang
 * @ClassName: TwoThreadPrintTest
 * @Description: 两个线程交替打印1-100
 * @date 2018/5/8 23:41
 */
public class TwoThreadPrintTest {

    public static void main(String[] args) {
        CountThread countThread = new CountThread(new AtomicInteger(1));
        new Thread(countThread).start();
        new Thread(countThread).start();
    }


    static class CountThread implements Runnable {

        private AtomicInteger count;

        public CountThread(AtomicInteger count) {
            this.count = count;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (TwoThreadPrintTest.class) {

                    // 唤醒等待线程
                    TwoThreadPrintTest.class.notify();
                    int number = count.getAndIncrement();
                    // 大于100，结束
                    if (number > 100) {
                        return;
                    }
                    System.out.println(Thread.currentThread().getName() + "," + number);
                    // 如果为奇数，线程进入等待状态
                    if (number % 2 == 1) {
                        try {
                            TwoThreadPrintTest.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 如果为偶数，线程进入等待状态
                    else if (number % 2 == 0) {
                        try {
                            TwoThreadPrintTest.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
/*
两个线程交替打印1-100

程序输出：
Thread-0,1
Thread-1,2
Thread-0,3
Thread-1,4
Thread-0,5
Thread-1,6
Thread-0,7
...
 */
