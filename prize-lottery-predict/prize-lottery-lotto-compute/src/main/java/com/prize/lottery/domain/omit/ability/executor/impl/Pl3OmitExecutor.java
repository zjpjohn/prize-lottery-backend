package com.prize.lottery.domain.omit.ability.executor.impl;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.ability.executor.OmitExecutor;
import com.prize.lottery.domain.omit.model.LotteryOmitDo;
import com.prize.lottery.domain.omit.model.LottoN3OmitDo;
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
public class Pl3OmitExecutor implements OmitExecutor {

    private final LotteryInfoMapper      lotteryMapper;
    private final ILotteryOmitRepository omitRepository;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.PL3;
    }

    @Override
    public void load() {
        LotteryOmitDo omit = LottoN3OmitDo.load(LotteryEnum.PL3).toOmit();
        omitRepository.save(omit);
    }

    @Override
    public void initialize() {
        LotteryOmitDo lotteryOmit = omitRepository.latestOmit(LotteryEnum.PL3)
                                                  .orElseGet(() -> LottoN3OmitDo.load(LotteryEnum.PL3).toOmit());

        List<LotteryInfoPo> lotteries = lotteryMapper.getLotteryInfoGtPeriod(LotteryEnum.PL3.getType(), lotteryOmit.getPeriod());
        Assert.state(!CollectionUtils.isEmpty(lotteries), ResponseHandler.NO_OPEN_LOTTERY);

        List<LotteryOmitDo> omitList = Lists.newArrayList();
        LottoN3OmitDo       last     = lotteryOmit.toN3Omit();
        for (LotteryInfoPo lottery : lotteries) {
            LottoN3OmitDo omit = last.nextOmit(lottery.getPeriod(), lottery.getRed());
            omitList.add(omit.toOmit());
            last = omit;
        }
        omitRepository.saveBatch(omitList);
    }

    @Override
    public void nextOmit(String period) {
        LotteryInfoPo lottery = lotteryMapper.getLotteryInfo(LotteryEnum.PL3.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        String        lastPeriod  = LotteryEnum.PL3.lastPeriod(lottery.getPeriod());
        LotteryOmitDo lotteryOmit = omitRepository.ofPeriod(LotteryEnum.PL3, lastPeriod);
        LotteryOmitDo omit        = lotteryOmit.toN3Omit().nextOmit(lottery.getPeriod(), lottery.getRed()).toOmit();
        omitRepository.save(omit);
    }

}
