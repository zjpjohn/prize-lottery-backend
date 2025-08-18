package com.prize.lottery.domain.omit.ability.executor.impl;

import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.ability.executor.MatchExecutor;
import com.prize.lottery.domain.omit.model.LotteryMatchOmitDo;
import com.prize.lottery.domain.omit.repository.ILotteryMatchRepository;
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
public class OmitMatchExecutor implements MatchExecutor {

    private final LotteryInfoMapper       mapper;
    private final ILotteryMatchRepository repository;

    @Override
    public void load(LotteryEnum type) {
        LotteryMatchOmitDo matchOmit = LotteryMatchOmitDo.load(type);
        repository.save(matchOmit);
    }

    @Override
    public void initialize(LotteryEnum type) {
        LotteryMatchOmitDo  matchOmitDo = repository.latestOmit(type);
        List<LotteryInfoPo> lotteries   = mapper.getLotteryInfoGtPeriod(type.getType(), matchOmitDo.getPeriod());
        Assert.state(CollectionUtils.isNotEmpty(lotteries), ResponseHandler.NO_OPEN_LOTTERY);

        List<LotteryMatchOmitDo> omitList = Lists.newArrayList();
        LotteryMatchOmitDo       last     = matchOmitDo;
        for (LotteryInfoPo lottery : lotteries) {
            LotteryMatchOmitDo nextOmit = last.nextOmit(lottery.getPeriod(), lottery.getRed());
            omitList.add(nextOmit);
            last = nextOmit;
        }
        repository.saveBatch(omitList);
    }

    @Override
    public void nextOmit(String period, LotteryEnum type) {
        LotteryInfoPo lottery = mapper.getLotteryInfo(type.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        String             lastPeriod  = type.lastPeriod(period);
        LotteryMatchOmitDo trendOmitDo = repository.ofPeriod(type, lastPeriod);
        LotteryMatchOmitDo nextOmit    = trendOmitDo.nextOmit(period, lottery.getRed());
        repository.save(nextOmit);
    }

}
