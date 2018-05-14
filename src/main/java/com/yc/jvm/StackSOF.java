/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.jvm;

/**
 * @author Yue Chang
 * @ClassName: StackSOF
 * @Description: 虚拟机栈和本地方法栈OOM测试示例
 * @date 2018/5/14 18:37
 * VM args：-Xss128k
 */
public class StackSOF {

    private int stackLength = 1;

    public void stackLength() {

        stackLength++;
        stackLength();
    }

    public static void main(String[] args) {
        StackSOF stackSOF = new StackSOF();
        try {
            stackSOF.stackLength();
        } catch (Throwable throwable) {
            System.out.println("stack length: " + stackSOF.stackLength);
            throw throwable;
        }
    }
}
/*
环境信息：
windows 7
jdk1.8.0_101
VM args：-Xss128k

代码分析：
在虚拟机规范中描述了两种异常：
 - 如果线程请求数量的栈深度大于虚拟机所允许的最大深度，将抛出StackOverFlowError异常
 - 如果虚拟机在扩展栈时无法申请到足够的内存空间，则抛出OutOfMemoryError异常
这里把异常分为两种情况，看似更加严谨，但却存在着一些互相重叠的地方：
当栈空间无法继续分配时，到底是内存太小，还是已使用的栈空间太大，其本质上只是对同一件事情的两种描述而已。
单线程的操作下，仅仅重现了StackOverFlowError异常。

程序输出：
Exception in thread "main" java.lang.StackOverflowError
stack length: 1381
	at com.yc.jvm.StackSOF.stackLength(StackSOF.java:21)
	at com.yc.jvm.StackSOF.stackLength(StackSOF.java:21)
	at com.yc.jvm.StackSOF.stackLength(StackSOF.java:21)
...后续异常堆栈信息省略
 */
