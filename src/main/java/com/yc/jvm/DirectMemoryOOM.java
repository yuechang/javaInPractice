/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author Yue Chang
 * @ClassName: DirectMemoryOOM
 * @Description: 本机直接内存溢出示例
 * @date 2018/5/15 10:07
 * VM args：-Xmx20m -XX:MaxDirectMemorySize=10m
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024*1024;

    public static void main(String[] args) throws IllegalAccessException {

        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
/*
环境信息：
windows 7
jdk1.8.0_101
VM args：-Xmx20m -XX:MaxDirectMemorySize=10m

代码分析：
DirectMemory容量可以通过-XX:MaxDirectMemorySize指定，如果不指定，则默认与Java堆最大值(-Xmx指定)一样。
由DirectMemory导致的内存溢出，一个明显的特征是在Heap Dump文件中不会看见明显的异常，
而程序中又直接或间接使用了NIO，那就可以考虑检查一下是不是这方面的原因

程序输出：
Exception in thread "main" java.lang.OutOfMemoryError
	at sun.misc.Unsafe.allocateMemory(Native Method)
	at com.yc.jvm.DirectMemoryOOM.main(DirectMemoryOOM.java:28)
 */