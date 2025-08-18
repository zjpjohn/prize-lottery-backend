package com.prize.lottery.domain.omit.ability.executor.impl;

import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.ability.executor.SumExecutor;
import com.prize.lottery.domain.omit.model.LottoSumOmitDo;
import com.prize.lottery.domain.omit.repository.ILotterySumRepository;
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
public class OmitSumExecutor implements SumExecutor {

    private final LotteryInfoMapper     mapper;
    private final ILotterySumRepository repository;

    @Override
    public void load(LotteryEnum type) {
        LottoSumOmitDo sumOmitDo = LottoSumOmitDo.load(type);
        repository.save(sumOmitDo);
    }

    @Override
    public void initialize(LotteryEnum type) {
        LottoSumOmitDo sumOmitDo = repository.latestOmit(type);

        List<LotteryInfoPo> lotteries = mapper.getLotteryInfoGtPeriod(type.getType(), sumOmitDo.getPeriod());
        Assert.state(CollectionUtils.isNotEmpty(lotteries), ResponseHandler.NO_OPEN_LOTTERY);

        List<LottoSumOmitDo> omitList = Lists.newArrayList();
        LottoSumOmitDo       last     = sumOmitDo;
        for (LotteryInfoPo lottery : lotteries) {
            LottoSumOmitDo sumOmit = last.nextOmit(lottery.getPeriod(), lottery.getRed());
            omitList.add(sumOmit);
            last = sumOmit;
        }
        repository.saveBatch(omitList);
    }

    @Override
    public void nextOmit(String period, LotteryEnum type) {
        LotteryInfoPo lottery = mapper.getLotteryInfo(type.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        String         lastPeriod = type.lastPeriod(period);
        LottoSumOmitDo sumOmitDo  = repository.ofPeriod(type, lastPeriod);
        LottoSumOmitDo nextOmit   = sumOmitDo.nextOmit(period, lottery.getRed());
        repository.save(nextOmit);
    }

}
