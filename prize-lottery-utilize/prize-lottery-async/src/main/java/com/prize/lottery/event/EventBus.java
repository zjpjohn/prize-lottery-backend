package com.prize.lottery.event;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class EventBus<T extends Event<T>> implements InitializingBean, DisposableBean {

    private final static Integer DEFAULT_BUFFER_SIZE = 1 << 12;

    private final Disruptor<T>       disruptor;
    private final EventTranslator<T> translator;

    /**
     * 默认事件构造器
     *
     * @param clazz 事件类型
     */
    public EventBus(Class<T> clazz) {
        this(clazz, new DefaultEventTranslator<>());
    }

    /**
     * 默认事件构造器
     *
     * @param clazz 事件类型
     */
    public EventBus(Class<T> clazz, Integer bufferSize) {
        this(bufferSize, clazz, new DefaultEventTranslator<>());
    }

    /**
     * 使用默认事件工厂和默认缓存大小
     *
     * @param clazz      事件类型
     * @param translator 事件转换器
     */
    public EventBus(Class<T> clazz, EventTranslator<T> translator) {
        this(DEFAULT_BUFFER_SIZE, new DefaultEventFactory<>(clazz), translator);
    }

    /**
     * 使用默认事件构造工厂
     *
     * @param bufferSize 事件缓存大小
     * @param clazz      事件类型
     * @param translator 事件值转换器
     */
    public EventBus(Integer bufferSize, Class<T> clazz, EventTranslator<T> translator) {
        this(bufferSize, new DefaultEventFactory<>(clazz), translator);
    }

    /**
     * @param bufferSize   事件缓存大小
     * @param eventFactory 事件构造工厂
     * @param translator   事件值转换器
     */
    public EventBus(Integer bufferSize, EventFactory<T> eventFactory, EventTranslator<T> translator) {
        this.translator = translator;
        this.disruptor  = new Disruptor<T>(eventFactory, bufferSize, new NamedThreadFactory("event-bus-", false));
    }

    /**
     * 单事件发送
     *
     * @param event 事件内容
     */
    public void publish(T event) {
        this.disruptor.publishEvent(this.translator, event);
    }

    /**
     * 批量事件发送
     *
     * @param events 事件集合
     */
    public void publish(T[] events) {
        this.disruptor.publishEvents(this.translator, events);
    }

    /**
     * 消费者-消费者之间重复消费事件
     */
    @SafeVarargs
    public final EventHandlerGroup<T> handleWith(EventHandler<T>... handlers) {
        return this.disruptor.handleEventsWith(handlers);
    }

    @Override
    public void destroy() {
        if (this.disruptor != null) {
            this.disruptor.shutdown();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (disruptor == null) {
            throw new IllegalArgumentException("disruptor instance require not null.");
        }
        disruptor.start();
    }
}
