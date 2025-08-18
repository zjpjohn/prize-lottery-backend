package com.prize.lottery.application.service.impl;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.application.service.IMasterEvictService;
import com.prize.lottery.domain.IMasterEvictAbility;
import com.prize.lottery.enums.LotteryEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MasterEvictService implements IMasterEvictService {

    private final EnumerableExecutorFactory<LotteryEnum, IMasterEvictAbility> evictFactory;

    @Override
    public void extractEvicts(LotteryEnum type) {
        evictFactory.ofNullable(type).ifPresent(IMasterEvictAbility::extractMasters);
    }

    @Override
    public void clearForecasts(LotteryEnum type) {
        evictFactory.ofNullable(type).ifPresent(IMasterEvictAbility::clearForecasts);
    }

    @Override
    public void clearMasters(LotteryEnum type) {
        evictFactory.ofNullable(type).ifPresent(IMasterEvictAbility::clearMasters);
    }

}
