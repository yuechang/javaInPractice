/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author Yue Chang
 * @ClassName: Mutex
 * @Description: 独占锁示例
 * @date 2018/4/28 10:15
 */
public class Mutex implements Lock{

    // 仅需要将操作代理到Sync上即可
    private final Sync sync = new Sync();

    @Override
    public void lock() { sync.acquire(1); }
    @Override
    public boolean tryLock() { return sync.tryAcquire(1); }
    @Override
    public void unlock() {  sync.release(1); }
    @Override
    public Condition newCondition() { return sync.newCondition(); }
    public boolean isLocked(){ return sync.isHeldExclusively(); }
    public boolean hasQueeudThreads(){ return sync.hasQueuedThreads(); }
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    // 静态内部类，自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer{
        // 是否处于占用状态
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        // 为状态为0的时候获取锁
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        // 释放锁，将状态设置为0
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        // 返回一个Condition，每个Condition都包含了一个condition队列
        Condition newCondition() {
            return new ConditionObject();
        }
    }
}
