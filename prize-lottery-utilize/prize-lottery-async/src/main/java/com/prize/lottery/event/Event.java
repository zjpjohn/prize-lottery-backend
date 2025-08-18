package com.prize.lottery.event;

public interface Event<T extends Event> {

    void assign(T event);
}
