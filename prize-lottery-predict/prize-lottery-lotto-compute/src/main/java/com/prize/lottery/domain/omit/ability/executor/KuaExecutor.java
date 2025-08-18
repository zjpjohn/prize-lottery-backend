package com.prize.lottery.domain.omit.ability.executor;


import com.prize.lottery.enums.LotteryEnum;

public interface KuaExecutor {

    void load(LotteryEnum type);

    void initialize(LotteryEnum type);

    void nextOmit(String period, LotteryEnum type);

}
