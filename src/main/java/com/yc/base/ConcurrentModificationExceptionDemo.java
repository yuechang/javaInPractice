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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yue Chang
 * @version 1.0
 * @className: ConcurrentModificationExceptionDemo
 * @description: 多线程ConcurrentModification异常显示
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

                    System.out.println("删除元素 value = " + value);

                    if (value.equals("3")) {
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

        List<String> resultList = new ArrayList<>();
        for (int i = 0 ;i < 10; i++) {

            resultList.add(i + "");
        }
        return resultList;
    }


}
