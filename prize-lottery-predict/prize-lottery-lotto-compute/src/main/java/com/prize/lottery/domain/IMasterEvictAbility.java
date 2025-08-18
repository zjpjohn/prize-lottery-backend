package com.prize.lottery.domain;


import com.cloud.arch.executor.Executor;
import com.prize.lottery.enums.LotteryEnum;

public interface IMasterEvictAbility extends Executor<LotteryEnum> {

    void extractMasters();

    void clearForecasts();

    void clearMasters();

}
