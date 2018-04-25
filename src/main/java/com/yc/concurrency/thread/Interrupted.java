/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: Interrupted
 * @Description: 线程中断示例
 * @date 2018/4/25 9:35
 */
public class Interrupted {

    public static void main(String[] args) throws InterruptedException {
        // sleepThread不停的尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        
        // busyThread不停的运行
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);

        sleepThread.start();
        busyThread.start();

        // 休眠5秒，让线程充分运行
        TimeUnit.SECONDS.sleep(5);

        sleepThread.interrupt();
        busyThread.interrupt();

        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        // 防止线程立刻退出
        SleepUtils.second(2);
    }


    static class SleepRunner implements Runnable{

        @Override
        public void run() {
            while (true){
                SleepUtils.second(10);
            }
        }
    }

    static class BusyRunner implements Runnable{

        @Override
        public void run() {
            while (true){
            }
        }
    }
}
/*
程序输出：
java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at java.lang.Thread.sleep(Thread.java:340)
	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
	at com.yc.concurrency.thread.SleepUtils.second(SleepUtils.java:19)
	at com.yc.concurrency.thread.Interrupted$SleepRunner.run(Interrupted.java:47)
	at java.lang.Thread.run(Thread.java:745)
SleepThread interrupted is false
BusyThread interrupted is true

从结果可以看出，抛出InterruptedException的线程SleepThread，其中断标识位被清除了，
而一直忙碌运作的线程BusyThread，中断标识位没有被清除。
 */