/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author Yue Chang
 * @ClassName: MultiThread
 * @Description: 线程信息打印示例
 * @date 2018/4/24 16:07
 */
public class MultiThread {

    public static void main(String[] args) {
        // 获取Java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，仅打印线程ID和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("["+threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
        }

    }
}
/*
环境信息：
windows 7
jdk1.8.0_101
输出信息：
[6]Monitor Ctrl-Break
[5]Attach Listener
[4]Signal Dispatcher
[3]Finalizer
[2]Reference Handler
[1]main

可以看到，一个Java程序的运行不仅仅是main()方法的运行，
而是main线程和多个其他线程的同时运行
*/