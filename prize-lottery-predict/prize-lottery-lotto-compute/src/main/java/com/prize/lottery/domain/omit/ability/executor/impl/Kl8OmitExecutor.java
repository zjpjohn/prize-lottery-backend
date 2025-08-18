package com.prize.lottery.domain.omit.ability.executor.impl;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.ability.executor.OmitExecutor;
import com.prize.lottery.domain.omit.model.LottoKl8OmitDo;
import com.prize.lottery.domain.omit.repository.ILottoKl8OmitRepository;
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
public class Kl8OmitExecutor implements OmitExecutor {

    private final LotteryInfoMapper       lotteryMapper;
    private final ILottoKl8OmitRepository kl8OmitRepository;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.KL8;
    }

    @Override
    public void load() {
        LottoKl8OmitDo kl8Omit = LottoKl8OmitDo.load();
        LotteryInfoPo  lottery = lotteryMapper.getLotteryInfo(LotteryEnum.KL8.getType(), kl8Omit.getPeriod());
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);
        kl8Omit.calcTailOmit(lottery.redBalls());
        kl8OmitRepository.save(kl8Omit);
    }

    @Override
    public void initialize() {
        LottoKl8OmitDo kl8Omit = kl8OmitRepository.latestOmit().orElseGet(LottoKl8OmitDo::load);

        List<LotteryInfoPo> lotteries = lotteryMapper.getLotteryInfoGtPeriod(LotteryEnum.KL8.getType(), kl8Omit.getPeriod());
        Assert.state(!CollectionUtils.isEmpty(lotteries), ResponseHandler.NO_OPEN_LOTTERY);

        List<LottoKl8OmitDo> omitList = Lists.newArrayList();
        LottoKl8OmitDo       last     = kl8Omit;
        for (LotteryInfoPo lotto : lotteries) {
            LottoKl8OmitDo current = last.nextOmit(lotto.getPeriod(), lotto.getRed());
            omitList.add(current);
            last = current;
        }
        kl8OmitRepository.saveBatch(omitList);
    }

    @Override
    public void nextOmit(String period) {
        LotteryInfoPo lottery = lotteryMapper.getLotteryInfo(LotteryEnum.KL8.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        String         lastPeriod = LotteryEnum.KL8.lastPeriod(lottery.getPeriod());
        LottoKl8OmitDo kl8Omit    = kl8OmitRepository.ofPeriod(lastPeriod);
        LottoKl8OmitDo nextOmit   = kl8Omit.nextOmit(lottery.getPeriod(), lottery.getRed());
        kl8OmitRepository.save(nextOmit);
    }

}
