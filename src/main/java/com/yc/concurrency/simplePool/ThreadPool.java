/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.simplePool;

/**
 * @author Yue Chang
 * @ClassName: ThreadPool
 * @Description: 简单线程池接口示例
 * @date 2018/4/26 17:13
 */
public interface ThreadPool<Job extends Runnable> {
    // 执行一个Job，这个Job需要实现Runnable
    void execute(Job job);
    // 关闭线程池
    void shutdown();
    // 增加工作者线程
    void addWorkers(int num);
    // 减少工作者线程
    void removeWorker(int num) throws IllegalAccessException;
    // 得到正在等待执行的任务数量
    int getJobSize();
}
