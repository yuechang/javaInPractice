/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.jvm.gc;

/**
 * @author Yue Chang
 * @ClassName: MinorGC
 * @Description: 新生代Minor GC
 * @date 2018/5/17 9:07
 * VM args：-Xmx20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps
 */
public class MinorGC {

    private static final int _1MB = 1024*1024;

    public static void testAllocation() {

        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];   // 出现一次Minor GC
    }

    public static void main(String[] args) {
        testAllocation();
    }
}
/*
环境信息：
windows 7
jdk1.8.0_101
VM args：-Xmx20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps

对象优先在Eden区分配
大对象直接进入老年代

尝试分配3个2MB大小和1个4MB大小的对象，在运行时通过-Xms20m，-Xmx20m、-Xmn10m这三个参数限制了Java堆大小为20MB，
不可扩展，其中10MB分配给新生代，剩下的10MB分配给老年代。-XX:SurvivorRatio=8决定了新生代中种的Eden区和一个
Survivor区的空间比例是8:1，从输出的结果也可以清楚的看到"eden space 8192K from space 1024K to   space 1024K"
的信息，新生代总可用空间为9216KB(Eden区 + 1个Survivor区的总容量)

[GC (Allocation Failure) [PSYoungGen: 6316K->824K(9216K)] 6316K->4928K(19456K), 0.0355085 secs]
由于Eden分配空间失败导致Minor GC，
Young Generation空间由PS处理器收集，空间使用由6316K变为824K
总的堆空间使用由6316K变为4928K


2018-05-17T10:34:19.384+0800: 0.143: [GC (Allocation Failure) [PSYoungGen: 6316K->824K(9216K)] 6316K->4928K(19456K), 0.0355085 secs] [Times: user=0.00 sys=0.00, real=0.03 secs]
Heap
 PSYoungGen      total 9216K, used 7205K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 77% used [0x00000000ff600000,0x00000000ffc3b628,0x00000000ffe00000)
  from space 1024K, 80% used [0x00000000ffe00000,0x00000000ffece030,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 10240K, used 4104K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 40% used [0x00000000fec00000,0x00000000ff002020,0x00000000ff600000)
 Metaspace       used 3285K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 359K, capacity 388K, committed 512K, reserved 1048576K

GC日志说明如下：
2018-05-17T10:34:19.384+0800 – GC事件(GC event)开始的时间点.
0.143 – GC时间的开始时间,相对于JVM的启动时间,单位是秒(Measured in seconds).
GC – 用来区分(distinguish)是 Minor GC 还是 Full GC 的标志(Flag). 这里的 GC 表明本次发生的是 Minor GC.
Allocation Failure – 引起垃圾回收的原因. 本次GC是因为年轻代中没有任何合适的区域能够存放需要分配的数据结构而触发的.
PSYoungGen – 使用的垃圾收集器的名字. DefNew 这个名字代表的是: 单线程(single-threaded), 采用标记复制(mark-copy)算法的, 使整个JVM暂停运行(stop-the-world)的年轻代(Young generation) 垃圾收集器(garbage collector).
6316K->824K – 在本次垃圾收集之前和之后的年轻代内存使用情况(Usage).
(9216K) – 年轻代的总的大小(Total size).
6316K->4928K – 在本次垃圾收集之前和之后整个堆内存的使用情况(Total used heap).
(19456K) – 总的可用的堆内存(Total available heap).
0.0355085 secs – GC事件的持续时间(Duration),单位是秒.
[Times: user=0.00 sys=0.00, real=0.03 secs] – GC事件的持续时间,通过多种分类来进行衡量:
    user – 此次垃圾回收, 垃圾收集线程消耗的所有CPU时间(Total CPU time).
    sys – 操作系统调用(OS call) 以及等待系统事件的时间(waiting for system event)
    real – 应用程序暂停的时间(Clock time). 由于串行垃圾收集器(Serial Garbage Collector)只会使用单个线程, 所以 real time 等于 user 以及 system time 的总和
 */