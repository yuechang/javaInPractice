/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.volatiletest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Yue Chang
 * @ClassName: VolatileFeatureTwoThreadPrintTest
 * @Description: volatile特性测试类
 * @date 2018/4/19 10:26
 */
public class VolatileFeatureTwoThreadPrintTest {

    class VolatileFeatureExample{
        volatile long v1 = 0L;                  // 使用volatile声明64位的long型变量
        public void set(long l){
            v1 = l;                             // 单个volatile变量的写
        }
        public void getAndIncreament(){
            v1++;                               // 复合(多个)volatile变量的读/写
        }
        public long get(){
            return v1;                          // 单个volatile变量的读
        }
    }

    class UsuallyExample{
        long v1 = 0L;                           // 64位的long型普通变量
        public synchronized void set(long l){   // 对单个的普通变量的写用同一个锁同步
            v1 = l;
        }
        public void getAndIncreament(){         // 普通方法调用
            long temp = get();                  // 调用已同步的读方法
            temp += 1L;                         // 普通写方法
            set(temp);                          // 调用已同步的写方法
        }
        public synchronized long get(){         // 对单个的普通变量的读用同一个锁同步
            return v1;
        }
    }
    /*
    - 一个volatile变量的单个读/写操作，与一个普通变量的读/写操作都是使用同一个锁来同步，它们之间的执行效果相同。

    - 锁的happens-before规则保证释放锁和获取锁的两个线程之间的内存可见性，这意味着对一个volatile变量的读，
    总是能看到(任意线程)对这个volatile变量最后的写入

    - 锁的语言决定了临界区代码的执行具有原子性。这意味着，即使是64位的long型和double型变量，只要它是volatile变量，
    对该变量的读\写就具有原子性。

    - 如果多个volatile操作活类似于volatile++这种复合操作，这些操作整体上不具有原子性

    简而言之，volatile变量自身具有下列特性。
    - 可见性。对一个volatile变量的读，总是能看到（任意线程）对这个volatile变量最后的写入。
    - 原子性：对任意单个volatile变量的读/写具有原子性，但类似于volatile++这种复合操作不具有原子性。
     */
    @Test
    public void test() throws InterruptedException, ExecutionException {
        testVolatile();

        /*
        执行结果：
        999802
        340472
         */
    }

    private void testVolatile() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newCachedThreadPool();

        final VolatileFeatureExample volatileFeatureExample = new VolatileFeatureExample();
        final UsuallyExample usuallyExample = new UsuallyExample();


        List<Callable<String>> taskList = new ArrayList<>();
        for (int i = 0 ; i < 1000 ; i++){

            Callable<String> volatileCallable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    for (int i = 0 ; i < 1000 ; i++){
                        volatileFeatureExample.getAndIncreament();
                    }
                    return Thread.currentThread().getName() + "," + volatileFeatureExample.get();
                }
            };

            Callable<String> usuallyCallable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    for (int i = 0 ; i < 1000 ; i++){
                        usuallyExample.getAndIncreament();
                    }
                    return "----------------------------"+Thread.currentThread().getName() + "," + usuallyExample.get();
                }
            };
            taskList.add(volatileCallable);
            taskList.add(usuallyCallable);
        }

        List<Future<String>> futures = service.invokeAll(taskList);
        for (Future<String> future : futures) {
            String result = future.get();
            System.out.println(result);
        }

        System.out.println();
        System.out.println(volatileFeatureExample.get());
        System.out.println(usuallyExample.get());
    }
}
