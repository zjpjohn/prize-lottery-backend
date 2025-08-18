package com.prize.lottery.domain.omit.ability.executor.impl;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.ability.executor.OmitExecutor;
import com.prize.lottery.domain.omit.model.LotteryOmitDo;
import com.prize.lottery.domain.omit.model.LottoDltOmitDo;
import com.prize.lottery.domain.omit.repository.ILotteryOmitRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DltOmitExecutor implements OmitExecutor {

    private final LotteryInfoMapper      lotteryMapper;
    private final ILotteryOmitRepository omitRepository;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.DLT;
    }

    @Override
    public void load() {
        LotteryOmitDo omit = LottoDltOmitDo.load().toOmit();
        omitRepository.save(omit);
    }

    @Override
    public void initialize() {
        LotteryOmitDo lotteryOmit = omitRepository.latestOmit(LotteryEnum.DLT)
                                                  .orElseGet(() -> LottoDltOmitDo.load().toOmit());

        List<LotteryInfoPo> lotteries = lotteryMapper.getLotteryInfoGtPeriod(LotteryEnum.DLT.getType(), lotteryOmit.getPeriod());
        Assert.state(!CollectionUtils.isEmpty(lotteries), ResponseHandler.NO_OPEN_LOTTERY);

        List<LotteryOmitDo> omitList = Lists.newArrayList();
        LottoDltOmitDo      last     = lotteryOmit.toDltOmit();
        for (LotteryInfoPo lottery : lotteries) {
            LottoDltOmitDo omit = last.nextOmit(lottery.getPeriod(), lottery.getRed(), lottery.getBlue());
            omitList.add(omit.toOmit());
            last = omit;
        }
        omitRepository.saveBatch(omitList);
    }

    @Override
    public void nextOmit(String period) {
        LotteryInfoPo lottery = lotteryMapper.getLotteryInfo(LotteryEnum.DLT.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        String        lastPeriod  = LotteryEnum.DLT.lastPeriod(lottery.getPeriod());
        LotteryOmitDo lotteryOmit = omitRepository.ofPeriod(LotteryEnum.DLT, lastPeriod);
        LotteryOmitDo dltOmit = lotteryOmit.toDltOmit()
                                           .nextOmit(lottery.getPeriod(), lottery.getRed(), lottery.getBlue())
                                           .toOmit();
        omitRepository.save(dltOmit);
    }

}
