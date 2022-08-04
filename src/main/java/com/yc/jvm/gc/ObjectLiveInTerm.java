/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.jvm.gc;

/**
 * @author Yue Chang
 * @ClassName: ObjectLiveInTerm
 * @Description:  长期存活的对象进入老年代示例
 * @date 2018/5/17 17:21
 * VM args：-Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 */
public class ObjectLiveInTerm {

    private static final int _1MB = 1024*1024;

    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        int size = _1MB / 4;
        allocation1 = new byte[size];
        // 什么时候进入老年代取决于XX:MaxTenuringThreshold设置
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];

        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testTenuringThreshold();
    }
}
/*
环境信息：
windows 7
jdk1.8.0_101
VM args：-Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution

设置-XX:MaxTenuringThreshold=15，对象也并未存活在survivor区域中，jdk版本升级进行了优化或者这个参数不起作用了。

动态对象年龄判断
    为了能更好地适应不同程序的内存状况，虚拟机并不是永远地要求对象的年龄必须达到了MaxTenuringThreshold才能晋升老年代，
    如果在Survivor空间中相同年龄所有对象大小的总和大于Survivor空间的一半，年龄大于或者等于该年龄的对象就可以直接进入老年代，无须等到MaxTenuringThreshold中要求的年龄。

VM args：-Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8  -XX:+PrintTenuringDistribution -XX:+UseParallelGC -XX:MaxTenuringThreshold=1
程序输出：
[GC (Allocation Failure) --[PSYoungGen: 6408K->6408K(9216K)] 14600K->14619K(19456K), 0.0115996 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 6408K->0K(9216K)] [ParOldGen: 8210K->5076K(10240K)] 14619K->5076K(19456K), [Metaspace: 3273K->3273K(1056768K)], 0.0047112 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
Heap
 PSYoungGen      total 9216K, used 4342K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 53% used [0x00000000ff600000,0x00000000ffa3d8a0,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 5076K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 49% used [0x00000000fec00000,0x00000000ff0f53e0,0x00000000ff600000)
 Metaspace       used 3284K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 359K, capacity 388K, committed 512K, reserved 1048576K

VM args：-Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8  -XX:+PrintTenuringDistribution -XX:+UseParallelGC -XX:MaxTenuringThreshold=15
程序输出：
[GC (Allocation Failure) --[PSYoungGen: 6572K->6572K(9216K)] 14764K->14807K(19456K), 0.0013246 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Ergonomics) [PSYoungGen: 6572K->0K(9216K)] [ParOldGen: 8234K->5077K(10240K)] 14807K->5077K(19456K), [Metaspace: 3279K->3279K(1056768K)], 0.0061780 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
Heap
 PSYoungGen      total 9216K, used 4178K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 51% used [0x00000000ff600000,0x00000000ffa14930,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 5077K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 49% used [0x00000000fec00000,0x00000000ff0f54e8,0x00000000ff600000)
 Metaspace       used 3286K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 359K, capacity 388K, committed 512K, reserved 1048576K
 */