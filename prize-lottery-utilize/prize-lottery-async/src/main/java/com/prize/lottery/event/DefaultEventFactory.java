package com.prize.lottery.event;

import com.lmax.disruptor.EventFactory;

public class DefaultEventFactory<T extends Event<T>> implements EventFactory<T> {

    private final Class<T> clazz;

    public DefaultEventFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T newInstance() {
        try {
            return this.clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
