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
public class Fc3dOmitExecutor implements OmitExecutor {

    private final LotteryInfoMapper      lotteryMapper;
    private final ILotteryOmitRepository omitRepository;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.FC3D;
    }

    @Override
    public void load() {
        LottoN3OmitDo n3Omit = LottoN3OmitDo.load(LotteryEnum.FC3D);
        omitRepository.save(n3Omit.toOmit());
    }

    @Override
    public void initialize() {
        LotteryOmitDo lotteryOmit = omitRepository.latestOmit(LotteryEnum.FC3D)
                                                  .orElseGet(() -> LottoN3OmitDo.load(LotteryEnum.FC3D).toOmit());

        List<LotteryInfoPo> lotteries = lotteryMapper.getLotteryInfoGtPeriod(LotteryEnum.FC3D.getType(), lotteryOmit.getPeriod());
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
        LotteryInfoPo lottery = lotteryMapper.getLotteryInfo(LotteryEnum.FC3D.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        String        lastPeriod  = LotteryEnum.FC3D.lastPeriod(lottery.getPeriod());
        LotteryOmitDo lotteryOmit = omitRepository.ofPeriod(LotteryEnum.FC3D, lastPeriod);
        LotteryOmitDo omit        = lotteryOmit.toN3Omit().nextOmit(lottery.getPeriod(), lottery.getRed()).toOmit();
        omitRepository.save(omit);
    }

}
