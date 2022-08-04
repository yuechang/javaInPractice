/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.concurrency.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: SleepUtils
 * @Description: 线程工具类
 * @date 2018/4/24 21:00
 */
public class SleepUtils {

    public static final void second(long seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

