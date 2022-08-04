/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.concurrency.cyclicBarrier;

import java.util.concurrent.*;

/**
 * @author Yue Chang
 * @ClassName: BankWaterService
 * @Description: CyclicBarrier计算银行流水示例
 * @date 2018/5/7 16:46
 */
public class BankWaterService implements Runnable {

    // 创建4个屏障，处理完之后执行当前类的run方法
    private CyclicBarrier c = new CyclicBarrier(4, this);
    // 假设只有4个sheet，所以只启动4个线程
    private Executor executor = Executors.newFixedThreadPool(4);
    // 保存每个sheet计算出的银行流水结果
    private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();

    public void count() {

        for (int i = 0; i < 4; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // 假装计算当前sheet的银行流水数据
                    sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
                    // 计算完成插入一个屏障
                    try {
                        c.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void run() {

        Integer sum = 0;
        // 汇总每个sheet计算出的结果
        for (Integer count : sheetBankWaterCount.values()) {
            sum += count;
        }
        // 将结果输出
        System.out.println(sum);
    }
}
/*
结果输出：
4

使用线程池创建4个线程，分别计算每个sheet里的数据，每个sheet计算结果是1，再由
BankWaterService线程汇总4个sheet计算出的结果

 */
