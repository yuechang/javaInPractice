/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.threadPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: ScheduledThreadPoolTest
 * @Description: 调度线程池(ScheduledThreadPool)示例
 * @date 2018/5/4 20:34
 */
public class ScheduledThreadPoolTest {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        Thread thread = new Thread(new MyThread());

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        //延迟执行thread，延迟时间为5秒
        scheduledExecutorService.schedule(thread, 5, TimeUnit.SECONDS);
        //延迟0秒，定时执行thread，每次执行间隔10秒
        scheduledExecutorService.scheduleAtFixedRate(thread, 0, 10, TimeUnit.SECONDS);
        //延迟0秒，定时执行thread，每次间隔为线程执行完成之后10秒
        scheduledExecutorService.scheduleWithFixedDelay(thread, 0, 10, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName() + " , " + sdf.format(new Date()));
    }

    static class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " , " +  sdf.format(new Date()));
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

