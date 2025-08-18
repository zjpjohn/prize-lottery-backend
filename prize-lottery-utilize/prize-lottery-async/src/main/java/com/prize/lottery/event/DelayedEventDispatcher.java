package com.prize.lottery.event;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class DelayedEventDispatcher<T extends Event<T>> implements InitializingBean, DisposableBean {

    private static final long DEFAULT_LOAD_UNTIL_TIME = TimeUnit.HOURS.toMillis(1);

    private final Random                      random     = new Random();
    private final Lock                        lock       = new ReentrantLock();
    private final AtomicBoolean               startState = new AtomicBoolean(false);
    private final EventBus<T>                 eventBus;
    private final DelayQueue<EventDelayed<T>> delayQueue;
    private final Thread                      delayWorker;

    public DelayedEventDispatcher(Class<T> clazz) {
        this.delayQueue  = new DelayQueue<>();
        this.delayWorker = new Thread(this::triggerDelayEvent);
        this.eventBus    = new EventBus<>(clazz);
    }

    public DelayedEventDispatcher(Integer bufferSize, Class<T> clazz) {
        this.delayQueue  = new DelayQueue<>();
        this.delayWorker = new Thread(this::triggerDelayEvent);
        this.eventBus    = new EventBus<>(clazz, bufferSize);
    }

    /**
     * 消费者-消费者之间重复消费事件
     */
    @SafeVarargs
    public final EventHandlerGroup<T> handleWith(EventHandler<T>... handlers) {
        return this.eventBus.handleWith(handlers);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.startState.compareAndSet(false, true)) {
            this.eventBus.afterPropertiesSet();
            this.delayWorker.start();
        }
    }

    @Override
    public void destroy() {
        this.eventBus.destroy();
    }

    /**
     * 推送事件立即调度执行
     *
     * @param event 事件信息
     */
    public void publish(T event) {
        this.eventBus.publish(event);
    }

    /**
     * 批量发布事件立即调度执行
     *
     * @param events 事件集合
     */
    public void publish(T[] events) {
        this.eventBus.publish(events);
    }

    /**
     * 推送事件，随机延迟指定范围随机时间
     *
     * @param event 事件
     */
    public void publish(T event, int range) {
        Preconditions.checkState(range > 1, "延迟范围应大于1秒");
        long            time         = this.getTimestamp() + random.nextInt(range) * 1000L;
        EventDelayed<T> eventDelayed = new EventDelayed<>(event, time);
        this.delayQueue.add(eventDelayed);
    }

    /**
     * 批量推送，随机到指定范围延迟时间
     *
     * @param events 事件集合
     */
    public void publish(T[] events, int range) {
        Preconditions.checkState(range > 1, "延迟范围应大于1秒");
        Long timestamp = this.getTimestamp();
        Arrays.stream(events).map(event -> {
            long time = timestamp + random.nextInt(range) * 1000L;
            return new EventDelayed<T>(event, time);
        }).forEach(this.delayQueue::add);
    }

    /**
     * 触发延迟队列到期事件
     */
    private void triggerDelayEvent() {
        do {
            try {
                EventDelayed<T> event = this.delayQueue.poll(DEFAULT_LOAD_UNTIL_TIME, TimeUnit.MILLISECONDS);
                while (event != null) {
                    eventBus.publish(event.getEvent());
                    event = this.delayQueue.take();
                }
            } catch (InterruptedException e) {
                log.error("延迟推送抓取事件异常:", e);
            }
        }
        while (this.startState.get() == Boolean.TRUE);
    }

    /**
     * 获取当前时间戳
     */
    private Long getTimestamp() {
        return System.currentTimeMillis();
    }

    @Getter
    public static class EventDelayed<E extends Event<E>> implements Delayed {

        private final E    event;
        private final Long timestamp;

        public EventDelayed(E event, Long timestamp) {
            this.event     = event;
            this.timestamp = timestamp;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.timestamp - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Ints.saturatedCast(this.timestamp.compareTo(((EventDelayed) o).getTimestamp()));
        }

    }

}
