/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: WaitNotify
 * @Description: 线程等待与通知示例
 * @date 2018/4/25 12:07
 */
public class WaitNotify {

    private static final Object lock = new Object();
    private static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        new Thread(new WaitThread(), "WaitThread").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(new NotifyThread(), "NotifyThread").start();
    }

    static class WaitThread implements Runnable{
        @Override
        public void run() {
            // 加锁，拥有lock的monitor
            synchronized (lock){
                // 当条件不满足时，继续等待，同事释放lock的锁
                while (flag){
                    try {
                        System.out.println(Thread.currentThread() + "flag is true. wait @"+
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 条件满足时，完成工作
                System.out.println(Thread.currentThread() + "flag is false. running @"+
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class NotifyThread implements Runnable{
        @Override
        public void run() {
            // 加锁，拥有lock的Monitor
            synchronized (lock){
                // 获取lock的锁，然后进行通知，通知时不会释放lock的锁
                // 直到当前线程释放了lock后，WaitThread才能从wait方法中返回
                System.out.println(Thread.currentThread() + " hold lock. notify @"+
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }

            // 再次加锁
            synchronized (lock){
                System.out.println(Thread.currentThread() + " hold lock again. sleep @"+
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(5);
            }
        }
    }
}
/*
环境信息：
windows 7
jdk1.8.0_101

输出信息：
Thread[WaitThread,5,main]flag is true. wait @14:30:17
Thread[NotifyThread,5,main] hold lock. notify @14:30:18
Thread[NotifyThread,5,main] hold lock again. sleep @14:30:23
Thread[WaitThread,5,main]flag is false. running @14:30:28

上述第3行和第4行输出的顺序可能会互换，而上述例子主要说明了调用wait()、notify()以
及notifyAll()时需要注意的细节，如下。
1）使用wait()、notify()和notifyAll()时需要先对调用对象加锁。
2）调用wait()方法后，线程状态由RUNNING变为WAITING，并将当前线程放置到对象的等待队列(WaitQueue)。
3）notify()或notifyAll()方法调用后，等待线程依旧不会从wait()返回，需要调用notify()或
notifyAll()的线程释放锁之后，等待线程才有机会从wait()返回。
4）notify()方法将等待队列中的一个等待线程从等待队列(WaitQueue)中移到同步队列(SynchronizedQueue)中，
而notifyAll()方法则是将等待队列中所有的线程全部移到同步队列，被移动的线程状态由WAITING变为
BLOCKED。
5）从wait()方法返回的前提是获得了调用对象的锁。
从上述细节中可以看到，等待/通知机制依托于同步机制，其目的就是确保等待线程从
wait()方法返回时能够感知到通知线程对变量做出的修改。
 */