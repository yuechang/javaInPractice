/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.queue;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Yue Chang
 * @ClassName: DelayQueueTest
 * @Description: DelayQueue示例
 * @date 2018/5/4 17:05
 */
public class DelayQueueTest {

    private static final DelayQueue delayQueue = new DelayQueue();
    private static final AtomicLong sequencer = new AtomicLong(0);

    class ScheduledFutureTask<V> extends FutureTask<V> implements RunnableScheduledFuture<V> {

        private long time;
        private long period;
        private long sequenceNumber;

        /**
         * @param runnable
         * @param result
         * @param time
         * @param period
         * @param sequenceNumber 标识元素在队列中的先后顺序
         */
        public ScheduledFutureTask(Runnable runnable, V result, long time, long period, long sequenceNumber) {
            super(runnable, result);
            this.time = time;
            this.period = period;
            this.sequenceNumber = sequenceNumber;
        }


        /**
         *
         * @return
         */
        @Override
        public boolean isPeriodic() {
            return false;
        }

        // 返回当前元素还需要延时多长时间，单位是纳秒
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(time - now(), TimeUnit.NANOSECONDS);
        }

        private long now() {
            return System.nanoTime();
        }

        @Override
        public int compareTo(Delayed other) {


            //
            if (other == this)
                return 0;
            if (other instanceof ScheduledFutureTask) {
                ScheduledFutureTask<V> x = (ScheduledFutureTask<V>)other;
                long diff = time - x.time;
                if (diff < 0)
                    return -1;
                else if (diff > 0)
                    return 1;
                else if (sequenceNumber < x.sequenceNumber)
                    return -1;
                else
                    return 1;
            }
            long d = getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS);
            return (d == 0) ? 0 : (d < 0) ? -1 : 1;
        }
    }


}
