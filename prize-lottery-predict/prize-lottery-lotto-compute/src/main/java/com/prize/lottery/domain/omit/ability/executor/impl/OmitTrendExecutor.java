package com.prize.lottery.domain.omit.ability.executor.impl;

import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.ability.executor.TrendExecutor;
import com.prize.lottery.domain.omit.model.LotteryTrendOmitDo;
import com.prize.lottery.domain.omit.repository.ILotteryTrendRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OmitTrendExecutor implements TrendExecutor {

    private final LotteryInfoMapper       mapper;
    private final ILotteryTrendRepository repository;

    @Override
    public void load(LotteryEnum type) {
        LotteryTrendOmitDo trendOmit = LotteryTrendOmitDo.load(type);
        repository.save(trendOmit);
    }

    @Override
    public void initialize(LotteryEnum type) {
        LotteryTrendOmitDo  trendOmitDo = repository.latestOmit(type);
        List<LotteryInfoPo> lotteries   = mapper.getLotteryInfoGtPeriod(type.getType(), trendOmitDo.getPeriod());
        Assert.state(CollectionUtils.isNotEmpty(lotteries), ResponseHandler.NO_OPEN_LOTTERY);

        List<LotteryTrendOmitDo> omitList = Lists.newArrayList();
        LotteryTrendOmitDo       last     = trendOmitDo;
        for (LotteryInfoPo lottery : lotteries) {
            LotteryTrendOmitDo nextedOmit = last.nextOmit(lottery.getPeriod(), lottery.getRed());
            omitList.add(nextedOmit);
            last = nextedOmit;
        }
        repository.saveBatch(omitList);
    }

    @Override
    public void nextOmit(String period, LotteryEnum type) {
        LotteryInfoPo lottery = mapper.getLotteryInfo(type.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        String             lastPeriod  = type.lastPeriod(period);
        LotteryTrendOmitDo trendOmitDo = repository.ofPeriod(type, lastPeriod);
        LotteryTrendOmitDo nextedOmit  = trendOmitDo.nextOmit(period, lottery.getRed());
        repository.save(nextedOmit);
    }

}
