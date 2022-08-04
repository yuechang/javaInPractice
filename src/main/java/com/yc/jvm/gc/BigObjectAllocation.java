/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.jvm.gc;

/**
 * @author Yue Chang
 * @ClassName: BigObjectAllocation
 * @Description: 大对象直接进入老年代示例
 * @date 2018/5/17 12:11
 * VM args：-Xmx20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 */
public class BigObjectAllocation {

    private static final int _1MB = 1024*1024;

    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testPretenureSizeThreshold();
    }
}
/*
环境信息：
windows 7
jdk1.8.0_101
VM args：-Xmx20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728

3145728 = 3*1024*1024,单位字节(B)
由于Parallel Scavenge收集器对PretenureSizeThreshold参数无效，于是也就还是分配到了Young Generation上

Heap
 PSYoungGen      total 9216K, used 6480K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 79% used [0x00000000ff600000,0x00000000ffc543e0,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 0K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 0% used [0x00000000fec00000,0x00000000fec00000,0x00000000ff600000)
 Metaspace       used 3245K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 353K, capacity 388K, committed 512K, reserved 1048576K

 */