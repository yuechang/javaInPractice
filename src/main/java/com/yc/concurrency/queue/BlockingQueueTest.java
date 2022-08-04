/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.concurrency.queue;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * BlockingQueue测试类
 *
 * @author Yue Chang
 * @date 2018/1/25 15:58
 */
public class BlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {

        // 声明一个容量为10的缓存队列
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);

        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("BlockingQueueTest-pool-%d").build();

        // 借助Executors
        ExecutorService service = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        // 启动线程
        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(consumer);

        // 执行10s
        Thread.sleep(10 * 1000);
        producer1.stop();
        producer2.stop();
        producer3.stop();

        Thread.sleep(2000);
        // 退出Executor
        service.shutdown();
    }
}
