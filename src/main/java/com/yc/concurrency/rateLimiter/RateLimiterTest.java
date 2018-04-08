/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.rateLimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * @author Yue Chang
 * @ClassName: RateLimiterTest
 * @Description: 令牌桶实例
 * @date 2018/4/8 22:30
 */
public class RateLimiterTest {

    public static void main(String[] args) throws Exception{
        // 信号量
        final CountDownLatch signal = new CountDownLatch(1);
        // 创建每秒50次请求的令牌桶
        final RateLimiter rateLimiter = RateLimiter.create(50);

        ExecutorService executorService = Executors.newCachedThreadPool();

        final AtomicInteger acquireSuccessCount = new AtomicInteger(0);
        final AtomicInteger acquireFailureCount = new AtomicInteger(0);
        List<CompletableFuture> list = new ArrayList<>();
        for (int i = 0 ; i < 100 ; i++){
            CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(new Supplier<Boolean>() {
                @Override
                public Boolean get() {
                    // 所有线程均在此次阻塞等待，释放后竞争获取令牌
                    try {
                        signal.await();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    boolean flag = rateLimiter.tryAcquire();
                    if (flag) {
                        System.out.println(Thread.currentThread().getName() + ",成功获得令牌锁");
                    } else {
                        System.out.println(Thread.currentThread().getName() + ",未获得令牌锁");
                    }
                    if (flag) {
                        acquireSuccessCount.incrementAndGet();
                    } else {
                        acquireFailureCount.incrementAndGet();
                    }
                    return flag;
                }
            },executorService);
            list.add(completableFuture);
           /*
           final Future<Boolean> future = executorService.submit(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    // 所有线程均在此次阻塞等待，释放后竞争获取令牌
                    signal.await();
                    Thread.sleep(2000);
                    boolean flag = rateLimiter.tryAcquire();
                    if (flag) {
                        System.out.println(Thread.currentThread().getName() + ",成功获得令牌锁");
                    } else {
                        System.out.println(Thread.currentThread().getName() + ",未获得令牌锁");
                    }
                    if (flag){
                        acquireSuccessCount.incrementAndGet();
                    } else {
                        acquireFailureCount.incrementAndGet();
                    }
                    return flag;
                }
            });*/
        }
        signal.countDown();
        CompletableFuture<Void> all = CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));
        // 阻塞所有线程到结束
        all.get();

        System.out.println("成功获得令牌桶数量：" + acquireSuccessCount);
        System.out.println("未能成功获得令牌桶数量：" + acquireFailureCount);
    }
}

