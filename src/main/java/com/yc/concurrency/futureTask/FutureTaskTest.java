/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.futureTask;

import java.util.concurrent.*;

/**
 * @author Yue Chang
 * @ClassName: FutureTaskTest
 * @Description: FutureTask线程等待示例
 * @date 2018/5/8 21:55
 */
public class FutureTaskTest {
    //
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

