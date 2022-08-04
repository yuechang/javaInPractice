/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.concurrency.futureTask;

import java.util.concurrent.*;

/**
 * @author Yue Chang
 * @ClassName: FutureTaskTest
 * @Description: FutureTask线程等待示例
 * @date 2018/5/8 21:55
 */
public class FutureTaskTest {
    // 线程名称与对应的返回结果对象
    private final ConcurrentMap<Object, Future<String>> taskCache = new ConcurrentHashMap<>();

    private String executionTask(final String taskName) throws ExecutionException, InterruptedException {
        while (true) {
            Future<String> future = taskCache.get(taskName);

            if (future == null) {
                Callable<String> task = new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        System.out.println(Thread.currentThread().getName() + "," + taskName);
                        return taskName;
                    }
                };
                FutureTask<String> futureTask = new FutureTask<>(task);
                future = taskCache.putIfAbsent(taskName, futureTask);
                if (future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }

            try {
                return future.get();
            } catch (CancellationException e) {
                taskCache.remove(taskName, future);
            }
        }
    }

    public static void main(String[] args) {
        FutureTaskTest futureTaskTest = new FutureTaskTest();
        MyThread myThread = new MyThread(futureTaskTest);
        new Thread(myThread).start();
        new Thread(myThread).start();
    }

    static class MyThread implements Runnable {

        private FutureTaskTest futureTaskTest;

        public MyThread(FutureTaskTest futureTaskTest) {
            this.futureTaskTest = futureTaskTest;
        }

        @Override
        public void run() {
            String result = null;
            try {
                result = futureTaskTest.executionTask("futureTask");
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
/*
输出结果：
Thread-0,futureTask

当一个线程需要等待另一个线程把某个任务执行完后它才能继续执行，此时可以使用FutureTask

当多个线程试图同时执行同一个任务时，只允许一个线程执行任务，
其他线程需要等待这个任务执行完后才能继续执行
 */
