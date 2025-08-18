package com.prize.lottery.event;

import com.lmax.disruptor.EventTranslatorOneArg;

public interface EventTranslator<T extends Event<T>> extends EventTranslatorOneArg<T, T> {

}
