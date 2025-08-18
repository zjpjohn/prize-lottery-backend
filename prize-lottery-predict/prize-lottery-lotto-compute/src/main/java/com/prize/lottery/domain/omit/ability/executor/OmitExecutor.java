package com.prize.lottery.domain.omit.ability.executor;


import com.cloud.arch.executor.Executor;
import com.prize.lottery.enums.LotteryEnum;

public interface OmitExecutor extends Executor<LotteryEnum> {

    void load();

    void initialize();

    void nextOmit(String period);
}
