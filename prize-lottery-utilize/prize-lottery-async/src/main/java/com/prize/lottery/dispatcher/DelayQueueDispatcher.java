package com.prize.lottery.dispatcher;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class DelayQueueDispatcher<T> implements InitializingBean, DisposableBean {

    private static final long DEFAULT_LOAD_UNTIL_TIME = TimeUnit.HOURS.toMillis(1);

    private final Random                    random     = new Random();
    private final AtomicBoolean             startState = new AtomicBoolean(false);
    private final DelayQueue<DelayEvent<T>> delayQueue;
    private final Thread                    delayWorker;
    private final Executor                  executor;
    private final EventHandler<T>           handler;

    public DelayQueueDispatcher(Executor executor, EventHandler<T> handler) {
        this.executor    = executor;
        this.handler     = handler;
        this.delayQueue  = new DelayQueue<>();
        this.delayWorker = new Thread(this::triggerDelayTask);
    }

    private void triggerDelayTask() {
        do {
            try {
                DelayEvent<T> request = this.delayQueue.poll(DEFAULT_LOAD_UNTIL_TIME, TimeUnit.MILLISECONDS);
                while (request != null) {
                    T command = request.getEvent();
                    this.executor.execute(() -> this.doExecute(command));
                    request = this.delayQueue.take();
                }
            } catch (InterruptedException e) {
                log.error("trigger delay task interrupted:", e);
            }
        }
        while (this.startState.get());
    }

    /**
     * 执行任务
     */
    private void doExecute(T event) {
        try {
            this.handler.handle(event);
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
    }

    /**
     * 立即执行事件
     *
     * @param event 事件
     */
    public void publish(T event) {
        if (event != null) {
            this.executor.execute(() -> this.doExecute(event));
        }

    }

    /**
     * 立即执行事件集合
     *
     * @param events 事件集合
     */
    public void publish(T[] events) {
        if (events == null) {
            return;
        }
        for (T event : events) {
            this.executor.execute(() -> this.doExecute(event));
        }
    }

    /**
     * @param event 延迟事件
     * @param range 随机延迟时间范围
     */
    public void publish(T event, int range) {
        Preconditions.checkState(range > 1, "延迟范围应大于1秒");
        long          current    = System.currentTimeMillis();
        long          deliverAt  = current + random.nextInt(range) * 1000L;
        DelayEvent<T> delayEvent = new DelayEvent<>(deliverAt, event);
        this.delayQueue.add(delayEvent);
    }

    /**
     * 批量提交延迟事件
     *
     * @param events 事件集合
     * @param range  随机延迟时间范围(单位:秒)
     */
    public void publish(T[] events, int range) {
        Preconditions.checkState(range > 1, "延迟范围应大于1秒");
        long current = System.currentTimeMillis();
        Arrays.stream(events).map(event -> {
            long deliverAt = current + random.nextInt(range) * 1000L;
            return new DelayEvent<>(deliverAt, event);
        }).forEach(this.delayQueue::add);
    }

    @Override
    public void destroy() throws Exception {
        this.startState.set(false);
        if (!this.delayWorker.isInterrupted()) {
            this.delayWorker.interrupt();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.startState.compareAndSet(false, true)) {
            this.delayWorker.start();
        }
    }

    @Getter
    private final static class DelayEvent<T> implements Delayed {

        private final Long timestamp;
        private final T    event;

        public DelayEvent(long timestamp, T event) {
            this.timestamp = timestamp;
            this.event     = event;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.timestamp - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        @SuppressWarnings("rawtypes")
        public int compareTo(@Nonnull Delayed other) {
            return Ints.saturatedCast(this.timestamp.compareTo(((DelayEvent) other).getTimestamp()));
        }
    }

}
