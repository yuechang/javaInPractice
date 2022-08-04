/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.concurrency.exchanger;

import java.util.concurrent.*;

/**
 * @author Yue Chang
 * @ClassName: ExchangerTest
 * @Description: Exchanger示例
 * @date 2018/5/7 17:12
 */
public class ExchangerTest {

    private static Exchanger<String> exchanger = new Exchanger<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                // 录入的数据A
                String A = "银行流水A";
                try {
                    TimeUnit.SECONDS.sleep(2);
                    String B = exchanger.exchange(A);
                    System.out.println(Thread.currentThread().getName() + ",A和B数据是否一致：" + A.equals(B) + ",A录入的是：" + A + ",B录入的是：" + B);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                // 录入的数据A
                String B = "银行流水B";
                try {
                    String A = exchanger.exchange(B);
                    System.out.println(Thread.currentThread().getName() + ",A和B数据是否一致：" + A.equals(B) + ",A录入的是：" + A + ",B录入的是：" + B);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // 关闭线程池
        threadPool.shutdown();
    }
}

