/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.concurrency.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: Join
 * @Description: join方法实现线程的顺序调用
 * @date 2018/4/25 19:52
 */
public class Join {

    public static void main(String[] args) throws InterruptedException {

        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++){
            // 每一个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回
            Thread thread = new Thread(new Domino(previous), String.valueOf(i));
            thread.start();
            previous = thread;
        }

        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName() + " terminate. ");

    }

    static class Domino implements Runnable{

        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }
}
/*

输出信息：
main terminate.
0 terminate.
1 terminate.
2 terminate.
3 terminate.
4 terminate.
5 terminate.
6 terminate.
7 terminate.
8 terminate.
9 terminate.

从上述输出可以看到，每个线程终止的前提是前驱线程的终止，每个线程等待前驱线程
终止后，才从join()方法返回，这里涉及了等待/通知机制（等待前驱线程结束，接收前驱线程结
束通知）。

当线程终止时，会调用线程自身的notifyAll()方法，会通知所有等待在该线程对象上的线
程。可以看到join()方法的逻辑结构与4.3.3节中描述的等待/通知经典范式一致，即加锁、循环
和处理逻辑3个步骤。
 */
