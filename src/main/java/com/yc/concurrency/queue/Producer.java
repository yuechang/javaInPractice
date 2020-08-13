/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Yue Chang
 * @ClassName: Producer
 * @Description: 生产者线程
 * @date 2018/1/25 16:00
 */
public class Producer implements Runnable{

    private volatile boolean isRunning = true;
    private BlockingQueue queue;
    private static AtomicInteger count = new AtomicInteger();
    private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String data = null;
        Random r = new Random();

        System.out.println("生产者线程启动！~");
        try{
            while (isRunning){
                System.out.println("正在生产数据...");
                Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));

                data = "data" + count.incrementAndGet();
                System.out.println("将数据" + data + "放入队列！~");
                if (queue.offer(data,2, TimeUnit.SECONDS)){
                    System.out.println("放入数据失败：" + data);
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();;
        }finally {
            System.out.println(Thread.currentThread().getName() + "生产者线程结束！~");
        }
    }

    public void stop() {
        isRunning = false;
    }
}
