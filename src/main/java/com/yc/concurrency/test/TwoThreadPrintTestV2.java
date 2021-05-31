/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.concurrency.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替打印1-100
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-05-31 8:09 下午
 */
public class TwoThreadPrintTestV2 {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        AtomicInteger count = new AtomicInteger(1);

        PrintThread printThread1 = new PrintThread(lock, condition, count);
        PrintThread printThread2 = new PrintThread(lock, condition, count);

        new Thread(printThread1).start();
        new Thread(printThread2).start();
    }

    static class PrintThread implements Runnable {

        private Lock lock;
        private Condition condition;
        private AtomicInteger count;

        public PrintThread(Lock lock, Condition condition, AtomicInteger count) {
            this.lock = lock;
            this.condition = condition;
            this.count = count;
        }

        @Override
        public void run() {

            while (true) {

                lock.lock();
                try {
                    condition.signal();
                    int number = count.getAndIncrement();
                    if (number > 100) {
                        return;
                    }
                    if (number % 2 == 1) {
                        System.out.println(Thread.currentThread().getName() + ", number : " + number);
                        condition.await();
                    } else if (number % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + ", number : " + number);
                        condition.await();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
