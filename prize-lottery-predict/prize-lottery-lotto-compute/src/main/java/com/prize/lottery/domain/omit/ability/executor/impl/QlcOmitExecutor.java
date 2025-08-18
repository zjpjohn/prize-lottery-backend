package com.prize.lottery.domain.omit.ability.executor.impl;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.ability.executor.OmitExecutor;
import com.prize.lottery.domain.omit.model.LotteryOmitDo;
import com.prize.lottery.domain.omit.model.LottoQlcOmitDo;
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
public class QlcOmitExecutor implements OmitExecutor {

    private final LotteryInfoMapper      lotteryMapper;
    private final ILotteryOmitRepository omitRepository;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.QLC;
    }

    @Override
    public void load() {
        LotteryOmitDo omit = LottoQlcOmitDo.load().toOmit();
        omitRepository.save(omit);
    }

    @Override
    public void initialize() {
        LotteryOmitDo lotteryOmit = omitRepository.latestOmit(LotteryEnum.QLC)
                                                  .orElseGet(() -> LottoQlcOmitDo.load().toOmit());

        List<LotteryInfoPo> lotteries = lotteryMapper.getLotteryInfoGtPeriod(LotteryEnum.QLC.getType(), lotteryOmit.getPeriod());
        Assert.state(!CollectionUtils.isEmpty(lotteries), ResponseHandler.NO_OPEN_LOTTERY);

        List<LotteryOmitDo> omitList = Lists.newArrayList();
        LottoQlcOmitDo      last     = lotteryOmit.toQlcOmit();
        for (LotteryInfoPo lottery : lotteries) {
            LottoQlcOmitDo omit = last.nextOmit(lottery.getPeriod(), lottery.getRed());
            omitList.add(omit.toOmit());
            last = omit;
        }
        omitRepository.saveBatch(omitList);
    }

    @Override
    public void nextOmit(String period) {
        LotteryInfoPo lottery = lotteryMapper.getLotteryInfo(LotteryEnum.QLC.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        String        lastPeriod  = LotteryEnum.QLC.lastPeriod(lottery.getPeriod());
        LotteryOmitDo lotteryOmit = omitRepository.ofPeriod(LotteryEnum.QLC, lastPeriod);
        LotteryOmitDo omit        = lotteryOmit.toQlcOmit().nextOmit(lottery.getPeriod(), lottery.getRed()).toOmit();
        omitRepository.save(omit);
    }

}
