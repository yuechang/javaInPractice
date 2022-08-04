/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.jvm.gc;

import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: ReferenceCountingGC
 * @Description: 引用计数算法示例
 * @date 2018/5/15 10:44
 * GC args：-XX:+PrintGCDetails
 */
public class ReferenceCountingGC {

    public Object instance = null;
    private static final int _1MB = 1024*1024;
    // 这个成员属性的唯一意义就是占点3内存， 以便能在GC日志中看清楚是否被回收过
    private byte[] bigeSize = new byte[2* _1MB];

    public static void testGC() {

        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        // 假设在这行发生GC，objA和objB是否能被回收
        System.gc();
    }

    public static void main(String[] args) throws InterruptedException {
        testGC();
    }
}
/*
环境信息：
windows 7
jdk1.8.0_101
VM args：-XX:+PrintGCDetails

通过分析GC日志，虚拟机并没有因为这两个对象互相引用就不回收它们，
这也从侧面说明虚拟机并不是通过引用计数算法来判断对象是否存活的

[GC (System.gc()) [PSYoungGen: 9352K->824K(76288K)] 9352K->832K(251392K), 0.0014356 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (System.gc()) [PSYoungGen: 824K->0K(76288K)] [ParOldGen: 8K->725K(175104K)] 832K->725K(251392K), [Metaspace: 3279K->3279K(1056768K)], 0.0042239 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
Heap
 PSYoungGen      total 76288K, used 655K [0x000000076b180000, 0x0000000770680000, 0x00000007c0000000)
  eden space 65536K, 1% used [0x000000076b180000,0x000000076b223ee8,0x000000076f180000)
  from space 10752K, 0% used [0x000000076f180000,0x000000076f180000,0x000000076fc00000)
  to   space 10752K, 0% used [0x000000076fc00000,0x000000076fc00000,0x0000000770680000)
 ParOldGen       total 175104K, used 725K [0x00000006c1400000, 0x00000006cbf00000, 0x000000076b180000)
  object space 175104K, 0% used [0x00000006c1400000,0x00000006c14b5698,0x00000006cbf00000)
 Metaspace       used 3286K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 359K, capacity 388K, committed 512K, reserved 1048576K
 */