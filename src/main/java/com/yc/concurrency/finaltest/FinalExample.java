/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.concurrency.finaltest;

/**
 * @author Yue Chang
 * @ClassName: FinalExample
 * @Description: Final例子
 * @date 2018/4/21 16:31
 */
public class FinalExample {

    /*
    ### final域的重排序规则
    对于final域，编译器和处理器要遵守两个重排序规则。
    - 在构造器内对一个final域的写入，与随后把这个被构造对象引用赋值给一个引用变量，这两个操作之间不能重排序
    - 初次读一个包含final域的对象的引用，与随后初次读这个final域，这两个操作之间不能重排序

    ### 写final域的重排序规则
    写final域的重排序规则禁止把final域的写重排序到构造函数之外。这个规则包含下面2个方面
    - JMM禁止编译器吧final域的写重排序到构造函数之外
    - 编辑器会在final域的写之后，构造函数return之前，插入一个StoreStore屏障。这个屏障禁止处理器吧final域的写重排序到构造函数之外

    ### 读final域的重排序规则
    读final域的重排序规则是,在一个线程中，初次读对象引用与初次读该对象包含的final与，JMM禁止处理器重排序这两个操作。
    编译器会在读final域操作前面插入一个LoadLoad屏障

     */

    int i;                          // 普通变量
    final int j;                    // final变量
    static FinalExample obj;
    public FinalExample(){          // 构造函数
        i = 1;                      // 写普通域
        j = 2;                      // 写final域
    }

    public static void writer(){    // 写线程A执行
        obj = new FinalExample();
    }

    public static void reader(){    // 读线程B执行
        FinalExample object = obj;  // 读对象引用
        int a = object.i;           // 读普通域
        int b = object.j;           // 读final域
    }


}
