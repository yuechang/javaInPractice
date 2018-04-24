/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.thread;

/**
 * @author Yue Chang
 * @ClassName: Daemon
 * @Description: 守护线程示例
 * @date 2018/4/24 22:02
 */
public class Daemon {

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable{
        @Override
        public void run() {
            try {
                SleepUtils.second(10);
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}

/*
运行Daemon程序，可以看到在终端或者命令提示符上没有任何输出.
因为main线程终止后，daemon线程立即终止，finally语句并不定执行
 */