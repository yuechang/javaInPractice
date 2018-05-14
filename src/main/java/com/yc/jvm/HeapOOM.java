/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yue Chang
 * @ClassName: HeapOOM
 * @Description: Java堆内存溢出异常测试
 * @date 2018/5/14 18:11
 * VM args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {

    static class OOMObject {}

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
/*
环境信息：
windows 7
jdk1.8.0_101
VM args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError

代码分析：
Java堆用于存储对象实例，只要不断地创建对象，并且保证GC Roots到对象之间有可达路径来避免垃圾回收机制清除这些对象，
那么在对象数量达到最大堆的容量限制后就会产生内存溢出异常

程序输出：
java.lang.OutOfMemoryError: Java heap space
Dumping heap to java_pid10312.hprof ...
Heap dump file created [28239187 bytes in 0.421 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3210)
	at java.util.Arrays.copyOf(Arrays.java:3181)
	at java.util.ArrayList.grow(ArrayList.java:261)
	at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:235)
	at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:227)
	at java.util.ArrayList.add(ArrayList.java:458)
	at com.yc.jvm.HeapOOM.main(HeapOOM.java:24)
 */
