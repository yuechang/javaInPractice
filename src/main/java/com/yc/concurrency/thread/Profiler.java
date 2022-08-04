/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.concurrency.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: Profiler
 * @Description: ThreadLocal记录程序运行时间示例
 * @date 2018/4/25 20:10
 */
public class Profiler {
    //
    private static final ThreadLocal<Long> TIME_THREADLOACL = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static final void begin(){

        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(TIME_THREADLOACL.get() + " , " + currentTimeMillis);
        TIME_THREADLOACL.set(currentTimeMillis);
    }

    public static final long end(){
        return System.currentTimeMillis() - TIME_THREADLOACL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + Profiler.end() + " mills");
    }
}
/*
输出结果如下所示：
1524658746081 , 1524658746081
Cost: 1015 mills

Profiler可以被复用在方法调用耗时统计的功能上，在方法的入口前执行begin()方法，在
方法调用后执行end()方法，好处是两个方法的调用不用在一个方法或者类中，比如在AOP（面
向方面编程）中，可以在方法调用前的切入点执行begin()方法，而在方法调用后的切入点执行
end()方法，这样依旧可以获得方法的执行耗时。
 */
