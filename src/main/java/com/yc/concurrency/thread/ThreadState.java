/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.thread;

/**
 * @author Yue Chang
 * @ClassName: ThreadState
 * @Description: 线程状态类示例
 * @date 2018/4/24 21:00
 */
public class ThreadState {

    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(),"WaitingThread").start();

        // 使用两个Blocked线程，一个获取锁成功，另一个被阻塞
        new Thread(new Blocked(),"BlockedThread-1").start();
        new Thread(new Blocked(),"BlockedThread-2").start();
    }


    // 该线程不断地进行睡眠
    static class TimeWaiting implements Runnable{
        @Override
        public void run() {
            while (true){
                SleepUtils.second(100);
            }
        }
    }

    // 该线程在Waiting.class实例上等待
    static class Waiting implements Runnable{
        @Override
        public void run() {
            while (true){
                synchronized (Waiting.class){
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 该线程在Blocked.class实例上加锁后，不会释放该锁
    static class Blocked implements Runnable{
        @Override
        public void run() {
            synchronized (Blocked.class){
                while (true){
                    SleepUtils.second(100);
                }
            }
        }
    }
}
/*
 - 运行代码之后，在cmd输入jps，查看ThreadState线程对应的pid，
 - jstack -pid可以得到下面线程状态信息，部分关键信息如下：
// BlockedThread-2一直在等待进入synchronize代码块，此时的状态为：BLOCKED
"BlockedThread-2" #14 prio=5 os_prio=0 tid=0x0000000018abf000 nid=0x4158 waiting for monitor entry [0x000000001985f000]
   java.lang.Thread.State: BLOCKED (on object monitor)
        at com.yc.concurrency.thread.ThreadState$Blocked.run(ThreadState.java:57)
        - waiting to lock <0x00000000d60ae7a8> (a java.lang.Class for com.yc.concurrency.thread.ThreadState$Blocked)
        at java.lang.Thread.run(Thread.java:745)
// BlockedThread-1获得synchronize锁，进入了代码块，由于一直在执行sleep，此时的状态为：TIMED_WAITING
"BlockedThread-1" #13 prio=5 os_prio=0 tid=0x0000000018ad2000 nid=0x994 waiting on condition [0x000000001975f000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at com.yc.concurrency.thread.SleepUtils.second(SleepUtils.java:19)
        at com.yc.concurrency.thread.ThreadState$Blocked.run(ThreadState.java:57)
        - locked <0x00000000d60ae7a8> (a java.lang.Class for com.yc.concurrency.thread.ThreadState$Blocked)
        at java.lang.Thread.run(Thread.java:745)
// WaitingThread线程获得进入synchronize块之后调用wait()方法,此时没有其他线程notify()/notifyAll(),也就处于WAITING状态
"WaitingThread" #12 prio=5 os_prio=0 tid=0x0000000018ac6800 nid=0x2288 in Object.wait() [0x000000001965f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x00000000d60ab658> (a java.lang.Class for com.yc.concurrency.thread.ThreadState$Waiting)
        at java.lang.Object.wait(Object.java:502)
        at com.yc.concurrency.thread.ThreadState$Waiting.run(ThreadState.java:42)
        - locked <0x00000000d60ab658> (a java.lang.Class for com.yc.concurrency.thread.ThreadState$Waiting)
        at java.lang.Thread.run(Thread.java:745)

// TimeWaitingThread一直在执行sleep，此时的状态为：TIMED_WAITING
"TimeWaitingThread" #11 prio=5 os_prio=0 tid=0x0000000018abb800 nid=0x2dc4 waiting on condition [0x000000001955e000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at com.yc.concurrency.thread.SleepUtils.second(SleepUtils.java:19)
        at com.yc.concurrency.thread.ThreadState$TimeWaiting.run(ThreadState.java:30)
        at java.lang.Thread.run(Thread.java:745)
 */
