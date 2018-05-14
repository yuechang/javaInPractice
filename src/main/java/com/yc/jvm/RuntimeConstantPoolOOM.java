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

        //
        List<String> list = new ArrayList<>();
        //
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
根据加上的-Xmx20m以及程序输出分析，在没有设置-Xmx20m时发生了GC操作，jdk这个版本已经对PermGen Space进行了优化处理

程序输出:
Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
	at java.lang.Integer.toString(Integer.java:331)
	at java.lang.String.valueOf(String.java:2959)
	at com.yc.jvm.RuntimeConstantPoolOOM.main(RuntimeConstantPoolOOM.java:26)
 */
