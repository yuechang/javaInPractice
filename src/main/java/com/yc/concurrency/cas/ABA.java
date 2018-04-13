/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author Yue Chang
 * @ClassName: ABA
 * @Description: ABA问题解决办法与注意点
 * @date 2018/4/12 16:02
 */
public class ABA {

    // 如果变量是Integer包装类，且变量的值大于IntegerCached的范围（-128到127）有可能会出现永远无法赋值成功的问题,
    // 注意：不在-128-127范围内的将是返回new Integer(i)，也就不相等了
    public static final int expectedReference = 300;
    // public static final int expectedReference = 100;
    public static final int newReference = 103;

    private static AtomicStampedReference<Integer> atomicStampedReference
            = new AtomicStampedReference<>(expectedReference,0);

    // 测试提交
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "执行开始！");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicStampedReference.compareAndSet(expectedReference, newReference,
                        atomicStampedReference.getStamp(),atomicStampedReference.getStamp() + 1);
                System.out.println(atomicStampedReference.getReference() + "," + atomicStampedReference.getStamp());
                atomicStampedReference.compareAndSet(newReference, expectedReference,
                        atomicStampedReference.getStamp(),atomicStampedReference.getStamp() + 1);
                System.out.println(atomicStampedReference.getReference() + "," + atomicStampedReference.getStamp());
                System.out.println(Thread.currentThread().getName() + "执行完毕！");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "执行开始！");
                int stamp = atomicStampedReference.getStamp();
                System.out.println("before sleep : stamp = " + stamp);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                stamp = atomicStampedReference.getStamp();
                System.out.println("after sleep : stamp = " + stamp);
                boolean flag = atomicStampedReference.compareAndSet(expectedReference, newReference,
                        atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
                System.out.println(flag);
                System.out.println(atomicStampedReference.getReference() + "," + atomicStampedReference.getStamp());
                System.out.println(Thread.currentThread().getName() + "执行完毕！");
            }
        });

        thread.start();
        thread2.start();

        thread.join();
        thread2.join();
    }
}
