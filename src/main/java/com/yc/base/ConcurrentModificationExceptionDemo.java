/*
 * Copyright (c) 2018, Yue Chang and/or its affiliates. All rights reserved.
 * Yue Chang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.yc.base;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 多线程ConcurrentModification异常显示
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2018年07月30日 17:57
 */
public class ConcurrentModificationExceptionDemo {

    public static void main(String[] args) {
        test();
    }

    private static void test() {

        final List<String> myList = createTestData();

        new Thread(new Runnable() {

            @Override
            public void run() {
                for (String string : myList) {
                    System.out.println("遍历集合 value = " + string);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {

                for (Iterator<String> it = myList.iterator(); it.hasNext();) {
                    String value = it.next();

                    System.out.println("删除时迭代元素 value = " + value);

                    if (value.equals("3")) {
                        System.out.println("删除元素 value = " + value);
                        it.remove();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private static List<String> createTestData() {

        return IntStream.range(0, 10).boxed().map(i -> i + "").collect(Collectors.toList());
    }
}
/*
一个线程迭代map中的值，另外一个线程迭代，并在特定的情况下，删除指定元素，此时会发生ConcurrentModificationException异常

遍历集合 value = 0
删除时迭代元素 value = 0
遍历集合 value = 1
删除时迭代元素 value = 1
删除时迭代元素 value = 2
遍历集合 value = 2
删除时迭代元素 value = 3
遍历集合 value = 3
删除元素 value = 3
删除时迭代元素 value = 4
Exception in thread "Thread-0" java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:909)
	at java.util.ArrayList$Itr.next(ArrayList.java:859)
	at com.yc.base.ConcurrentModificationExceptionDemo$1.run(ConcurrentModificationExceptionDemo.java:54)
	at java.lang.Thread.run(Thread.java:748)
删除时迭代元素 value = 5
删除时迭代元素 value = 6
删除时迭代元素 value = 7
删除时迭代元素 value = 8
删除时迭代元素 value = 9

 */