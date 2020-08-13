/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.lock;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yue Chang
 * @ClassName: ConditionBoundedBuffer
 * @Description: 使用两个显示条件变量的有界缓存
 * @date 2018/1/16 17:22
 */
public class ConditionBoundedBuffer<T> {

    private static final int BUFFER_SIZE = 10;
    protected final Lock lock = new ReentrantLock();
    // 条件谓语 ：notFull (count < item.length)
    private final Condition notFull = lock.newCondition();
    // 条件谓语 ：notEmpty (count > 0)
    private final Condition notEmpty = lock.newCondition();

    private final T[] items = (T[])new Object[BUFFER_SIZE];

    private int tail,head,count;

    // 阻塞并直到：notFull
    public void put(T x) throws InterruptedException {

        lock.lock();
        try {
            while (count == items.length){

                System.out.println("队列已满");
                notFull.await();
            }
            items[tail] = x;
            if (++tail == items.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
            System.out.println("放入新元素："+ x + ",总数：" + count);
        } finally {
            lock.unlock();
        }
    }

    // 阻塞并直到：notEmpty
    public T take() throws InterruptedException {

        lock.lock();
        try {
            while (count == 0){
                System.out.println("队列已空");
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;
            if(++head == items.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }


    @Test
    public void test() throws InterruptedException {

        final ConditionBoundedBuffer<String> instance = new ConditionBoundedBuffer<String>();

        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 1 ; i < 13 ; i++){
                    try {
                        instance.put(i+"");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 1 ; i < 16 ; i++){
                    String value = null;
                    try {
                        value = instance.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(value);
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
