/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.concurrency.map;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Yue Chang
 * @ClassName: HashMapDeadLoopTest
 * @Description: HashMap死循环示例
 * @date 2018/5/2 21:39
 */
public class HashMapDeadLoopTest {

    public static void main(String[] args) throws InterruptedException {

        final Map<String, String> map = new HashMap<>(2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf" + i).start();
                }
            }
        },"ftf");
        t.start();
        t.join();
    }
}
/*
环境信息：
windows 10
jdk1.8.0_101

未发生死锁，哈哈哈
 */
