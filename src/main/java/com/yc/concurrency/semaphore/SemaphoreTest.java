/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.concurrency.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author Yue Chang
 * @ClassName: SemaphoreTest
 * @Description: Semaphore测试类
 * @date 2018/4/10 23:21
 */
public class SemaphoreTest {

    private static final int THREAD_COUNT = 50;
    private static ExecutorService threadPool =
            Executors.newFixedThreadPool(THREAD_COUNT);
    private static Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {
        for (int i = 0 ; i < THREAD_COUNT ; i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        semaphore.acquire();
                        int availablePermits = semaphore.availablePermits();
                        System.out.println(Thread.currentThread().getName() + ",save data,availablePermits:" + availablePermits);
                        semaphore.release();
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
    }
}

