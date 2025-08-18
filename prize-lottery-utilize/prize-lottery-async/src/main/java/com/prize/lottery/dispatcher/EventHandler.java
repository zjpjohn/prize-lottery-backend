package com.prize.lottery.dispatcher;

public interface EventHandler<T> {

    void handle(T event);

}
