/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: Consumer
 * @Description: 消费者线程
 * @date 2018/1/25 16:26
 */
public class Consumer implements Runnable{

    private BlockingQueue<String> queue;
    private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        System.out.println("启动消费者线程！~");
        Random random = new Random();
        boolean isRunning = true;
        try{
            while(isRunning){
                System.out.println("正从队列中获取数据...");
                String data = queue.poll(2, TimeUnit.SECONDS);
                if (null != data){
                    System.out.println("拿到数据：" + data);
                    Thread.sleep(random.nextInt(DEFAULT_RANGE_FOR_SLEEP));
                } else {
                    // 超过2秒还没有数据，认为所有生产线程都已经退出，自动退出消费线程
                    isRunning = false;
                }
            }
        }catch (InterruptedException e) {

            e.printStackTrace();
            Thread.currentThread().interrupt();
        }finally {
            System.out.println("消费者线程退出！~");
        }
    }
}
