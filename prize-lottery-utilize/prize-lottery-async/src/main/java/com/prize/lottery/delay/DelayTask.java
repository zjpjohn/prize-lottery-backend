package com.prize.lottery.delay;

import com.google.common.primitives.Ints;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public interface DelayTask extends Delayed {

    Long timestamp();

    @Override
    default long getDelay(TimeUnit unit) {
        return unit.convert(this.timestamp() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    default int compareTo(Delayed o) {
        return Ints.saturatedCast(this.timestamp().compareTo(((DelayTask) o).timestamp()));
    }

}
