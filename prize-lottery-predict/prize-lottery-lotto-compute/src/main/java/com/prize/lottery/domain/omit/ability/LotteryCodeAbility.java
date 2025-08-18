package com.prize.lottery.domain.omit.ability;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.domain.omit.ability.executor.CodeExecutor;
import com.prize.lottery.enums.LotteryEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryCodeAbility {

    private final EnumerableExecutorFactory<LotteryEnum, CodeExecutor> executors;

    public void initialize(LotteryEnum type, Integer limit) {
        executors.ofNullable(type).ifPresent(executor -> executor.initialize(limit));
    }

    public void execute(LotteryEnum type, String period) {
        executors.ofNullable(type).ifPresent(executor -> executor.execute(period));
    }

}
