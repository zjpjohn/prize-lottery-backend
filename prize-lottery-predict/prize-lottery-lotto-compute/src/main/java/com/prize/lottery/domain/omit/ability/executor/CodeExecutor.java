package com.prize.lottery.domain.omit.ability.executor;


import com.cloud.arch.executor.Executor;
import com.prize.lottery.enums.LotteryEnum;

public interface CodeExecutor extends Executor<LotteryEnum> {

    /**
     * 万能码初始化计算
     */
    void initialize(Integer limit);

    /**
     * 计算指定期的万能码命中
     */
    void execute(String period);

}
