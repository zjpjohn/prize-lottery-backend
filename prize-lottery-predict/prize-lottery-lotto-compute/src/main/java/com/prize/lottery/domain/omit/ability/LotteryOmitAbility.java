package com.prize.lottery.domain.omit.ability;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.omit.ability.executor.OmitExecutor;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LotteryOmitAbility {

    private final LotteryInfoMapper                                    lotteryMapper;
    private final EnumerableExecutorFactory<LotteryEnum, OmitExecutor> executors;

    public void loadOmits() {
        int result = lotteryMapper.hasLotteryOmit();
        Assert.state(result == 0, ResponseHandler.HAS_INIT_OMIT);
        executors.executors().forEach(OmitExecutor::load);
    }

    public void initCalcOmit(LotteryEnum type) {
        executors.of(type).initialize();
    }

    @Async
    public void calcOmit(LotteryEnum type, String period) {
        executors.of(type).nextOmit(period);
    }

}
