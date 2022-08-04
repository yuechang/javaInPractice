/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.concurrency.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Yue Chang
 * @ClassName: CyclicBarrierTest2
 * @Description: CyclicBarrier示例
 * @date 2018/5/7 16:31
 */
public class CyclicBarrierTest2 {

    private static CyclicBarrier c = new CyclicBarrier(2,new A());

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        }).start();

        c.await();
        System.out.println(2);
    }


    static class A implements Runnable {

        @Override
        public void run() {
            System.out.println(3);
        }
    }
}
/*
结果输出：
3
1
2
或者
3
2
1

CyclicBarrier还提供一个更高级的构造函数CyclicBarrier（int parties，Runnable barrierAction），
用于在线程到达屏障时，优先执行barrierAction，方便处理更复杂的业务场景。

barrierAction是最先执行的，也就是输出结果3
 */
