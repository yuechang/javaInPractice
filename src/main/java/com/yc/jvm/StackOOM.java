/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.jvm;

/**
 * @author Yue Chang
 * @ClassName: StackOOM
 * @Description: 创建线程导致栈内存溢出示例
 * @date 2018/5/14 19:47
 */
public class StackOOM {

    private void dontStop() {
        while (true) {
        }
    }

    public void stackLeakByThread() {
        while (true) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        StackOOM stackOOM = new StackOOM();
        stackOOM.stackLeakByThread();
    }
}
/*
注意:特别提示一下，如果读者要尝试运行上面这段代码，记得要保存当前工作。
由于在Windows平台的虚拟机下，Java 的线程是映射到操作系统的内核线程上的，
因此上述代码执行时有较大风险，可能会导致系统假死。
反正我的神船是卡死了的，囧

环境信息：
windows 7
jdk1.8.0_101
VM args：-Xss128k

理论程序输出：
Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
 */
