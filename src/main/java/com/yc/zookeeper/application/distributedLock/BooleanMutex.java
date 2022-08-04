/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.zookeeper.application.distributedLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author Yue Chang
 * @ClassName: BooleanMutex
 * @Description: 互斥信号共享锁
 * @date 2018/4/1 17:13
 */
public class BooleanMutex {

    private Sync sync;

    public BooleanMutex(){
        sync = new Sync();
        set(false);
    }

    /**
     * 阻塞等待Boolean为true
     * @throws InterruptedException
     */
    public void lock() throws InterruptedException{
        sync.innerLock();
    }

    /**
     * 阻塞等待Boolean为true，允许设置超时时间
     * @param timeout
     * @param unit
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public void lockTimeout(long timeout, TimeUnit unit)
            throws InterruptedException, TimeoutException{
        sync.innerLock(unit.toNanos(timeout));
    }

    public void unlock(){
        set(true);
    }

    /**
     * 重新设置对应的Boolean mutex
     * @param mutex
     */
    public void set(boolean mutex) {
        if (mutex){
            sync.innerSetTrue();
        } else {
            sync.innerSetFalse();
        }
    }

    public boolean state(){
        return sync.innerState();
    }


    /**
     * 互斥信号共享锁
     */
    private final class Sync extends AbstractQueuedSynchronizer{

        /**
         * 状态为1，则唤醒被阻塞在状态为FALSE的所有线程
         */
        private static final int TRUE = 1;

        /**
         * 状态为0，则当前线程阻塞，等待被唤醒
         */
        private static final int FALSE = 0;

        /**
         * 返回值大于0，则执行；返回值小于0，则阻塞
         * @param arg
         * @return
         */
        @Override
        protected int tryAcquireShared(int arg) {
            return getState() == 1 ? 1 : -1;
        }

        /**
         * 实现AQS的接口，释放共享锁的判断
         * @param arg
         * @return
         */
        @Override
        protected boolean tryReleaseShared(int arg) {
            // 始终返回ture，代表可以release
            return true;
        }

        private boolean innerState(){
            return getState() == 1;
        }

        private void innerLock() throws InterruptedException{
            acquireSharedInterruptibly(0);
        }

        private void innerLock(long nanosTimeout)
                throws InterruptedException,TimeoutException{
            if (!tryAcquireSharedNanos(0,nanosTimeout)){
                throw new TimeoutException();
            }
        }

        private void innerSetTrue(){
            for (;;){
                int s = getState();
                if (s == TRUE){
                    // 直接退出
                    return;
                }
                // cas更新状态，避免并发更新true操作
                if (compareAndSetState(s,TRUE)){
                    // 释放一下锁对象，唤醒一下阻塞的Thread
                    releaseShared(FALSE);
                }
            }
        }

        private void innerSetFalse(){
            for (;;){
                int s = getState();
                if (s == FALSE){
                    // 直接退出
                    return;
                }
                if (compareAndSetState(s,FALSE)){
                    setState(FALSE);
                }
            }
        }



    }
}

