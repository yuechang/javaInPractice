/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.concurrency.thread;

import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: Priority
 * @Description: 线程优先级示例
 * @date 2018/4/24 16:18
 */
public class Priority {

    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) throws InterruptedException {

        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++){
           int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job,"Thread:" + i);
            thread.setPriority(priority);
            thread.start();
        }

        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;
        for (Job job : jobs){
            System.out.println("Job Priority : " + job.priority + ",Count : " + job.jobCount);
        }

    }

    static class Job implements Runnable{

        private int priority;
        private long jobCount;

        public Job(int priority){
            this.priority = priority;
        }
        @Override
        public void run() {

            while(notStart){
                Thread.yield();
            }
            while (notEnd){
                Thread.yield();
                jobCount++;
            }
        }
    }
}
/*
环境信息：
windows 7
jdk1.8.0_101

输出信息：
Job Priority : 1,Count : 10271291
Job Priority : 1,Count : 7245496
Job Priority : 1,Count : 12342910
Job Priority : 1,Count : 19514540
Job Priority : 1,Count : 9817191
Job Priority : 10,Count : 20728259
Job Priority : 10,Count : 20025533
Job Priority : 10,Count : 19497661
Job Priority : 10,Count : 27255294
Job Priority : 10,Count : 20728232

从输出可以看到线程优先级没有生效，优先级1和优先级10的Job计数的结果非常相近，
没有明显差距。这表示程序正确性不能依赖线程的优先级高低。
 */