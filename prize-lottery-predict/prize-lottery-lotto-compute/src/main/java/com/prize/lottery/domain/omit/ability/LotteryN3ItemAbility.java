package com.prize.lottery.domain.omit.ability;

import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LotteryItemOmitDo;
import com.prize.lottery.domain.omit.repository.ILotteryItemOmitRepository;
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
public class LotteryN3ItemAbility {

    private final LotteryInfoMapper          mapper;
    private final ILotteryItemOmitRepository repository;

    public void load(LotteryEnum type) {
        LotteryItemOmitDo itemOmit = LotteryItemOmitDo.load(type);
        repository.save(itemOmit);
    }

    public void initialize(LotteryEnum type) {
        LotteryItemOmitDo   latest    = repository.latest(type);
        List<LotteryInfoPo> lotteries = mapper.getLotteryInfoGtPeriod(type.getType(), latest.getPeriod());
        Assert.state(CollectionUtils.isNotEmpty(lotteries), ResponseHandler.NO_OPEN_LOTTERY);

        List<LotteryItemOmitDo> omitList = Lists.newArrayList();
        LotteryItemOmitDo       last     = latest;
        for (LotteryInfoPo lottery : lotteries) {
            LotteryItemOmitDo next = last.next(lottery.getPeriod(), lottery.getRed());
            omitList.add(next);
            last = next;
        }
        repository.saveBatch(omitList);
    }

    public void nextOmit(String period, LotteryEnum type) {
        LotteryInfoPo lottery = mapper.getLotteryInfo(type.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        String            lastPeriod = type.lastPeriod(period);
        LotteryItemOmitDo itemOmitDo = repository.ofPeriod(type, lastPeriod);
        LotteryItemOmitDo next       = itemOmitDo.next(period, lottery.getRed());
        repository.save(next);
    }

}
