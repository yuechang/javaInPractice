/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Yue Chang
 * @ClassName: RuntimeConstantPoolOOM
 * @Description: 运行时常量池导致的内存溢出示例
 * @date 2018/5/14 20:14
 * VM args: -XX:PermSize=10m -XX:MaxPermSize=10m -Xmx20m
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {

        // 使用List保持着常量池的引用，避免Full GC回收常量池行为
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add((UUID.randomUUID()+String.valueOf(i++)).intern());
        }
    }
}
/*
环境信息：
windows 7
jdk1.7.0_40
VM args：-XX:PermSize=10m -XX:MaxPermSize=10m -Xmx20m

jdk1.8已经没有PermGen space也就采用jdk1.7来测试，没有设置-Xmx20m，程序运行了30分钟都没有出现问题
通过jconsole查看程序内存使用情况，堆空间在慢慢增加，而PermGen Space基本维持在设置的10m以下,
jdk这个版本已经将常量池放入了堆中，当堆空间不足发生了GC操作

java.lang.OutOfMemoryError: GC overhead limit exceeded 这种情况发生的原因是, 程序基本上耗尽了所有的可用内存, GC也清理不了。
原因分析
JVM抛出 java.lang.OutOfMemoryError: GC overhead limit exceeded 错误就是发出了这样的信号: 执行垃圾收集的时间比例太大, 有效的运算量太小。
默认情况下, 如果GC花费的时间超过 98%, 并且GC回收的内存少于 2%, JVM就会抛出这个错误。

java.lang.OutOfMemoryError: GC overhead limit exceeded
注意, java.lang.OutOfMemoryError: GC overhead limit exceeded 错误只在连续多次 GC 都只回收了不到2%的极端情况下才会抛出。
假如不抛出 GC overhead limit 错误会发生什么情况呢? 那就是GC清理的这么点内存很快会再次填满, 迫使GC再次执行。
这样就形成恶性循环, CPU使用率一直是100%, 而GC却没有任何成果. 系统用户就会看到系统卡死 - 以前只需要几毫秒的操作, 现在需要好几分钟才能完成。
这也是一个很好的 快速失败原则 的案例。

程序输出:
Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
	at java.lang.Integer.toString(Integer.java:331)
	at java.lang.String.valueOf(String.java:2959)
	at com.yc.jvm.RuntimeConstantPoolOOM.main(RuntimeConstantPoolOOM.java:26)
 */
