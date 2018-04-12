/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Yue Chang
 * @ClassName: Counter
 * @Description: 循环cas原子操作测试类
 * @date 2018/4/12 15:01
 */
public class Counter{

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private int i = 0;
    public static void main(String[] args) throws InterruptedException {

        long startTimeMillis = System.currentTimeMillis();
        Counter counter = new Counter();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0 ; i < 100 ; i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int i = 0 ; i < 1000 ; i++){
                        counter.count();
                        counter.safeCount();
                    }
                }
            });
            threadList.add(thread);
        }
        for (Thread thread : threadList) {
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        long endTimeMillis = System.currentTimeMillis();
        System.out.println(counter.i);
        System.out.println(counter.atomicInteger.get());
        System.out.println(endTimeMillis - startTimeMillis);

    }

    private void safeCount(){
        for (;;){
            int atomicInt = atomicInteger.get();
            boolean flag = atomicInteger.compareAndSet(atomicInt, ++atomicInt);
            if (flag){
                break;
            }
        }
    }

    public void count(){
        i++;
    }
}
