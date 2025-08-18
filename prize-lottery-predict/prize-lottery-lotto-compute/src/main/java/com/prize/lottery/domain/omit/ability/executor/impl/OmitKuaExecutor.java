package com.prize.lottery.domain.omit.ability.executor.impl;

import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.ability.executor.KuaExecutor;
import com.prize.lottery.domain.omit.model.LottoKuaOmitDo;
import com.prize.lottery.domain.omit.repository.ILotteryKuaRepository;
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
public class OmitKuaExecutor implements KuaExecutor {

    private final LotteryInfoMapper     mapper;
    private final ILotteryKuaRepository repository;

    @Override
    public void load(LotteryEnum type) {
        LottoKuaOmitDo kuaOmit = LottoKuaOmitDo.load(type);
        repository.save(kuaOmit);
    }

    @Override
    public void initialize(LotteryEnum type) {
        LottoKuaOmitDo kuaOmitDo = repository.latestOmit(type);

        List<LotteryInfoPo> lotteries = mapper.getLotteryInfoGtPeriod(type.getType(), kuaOmitDo.getPeriod());
        Assert.state(CollectionUtils.isNotEmpty(lotteries), ResponseHandler.NO_OPEN_LOTTERY);

        List<LottoKuaOmitDo> omitList = Lists.newArrayList();
        LottoKuaOmitDo       last     = kuaOmitDo;
        for (LotteryInfoPo lottery : lotteries) {
            LottoKuaOmitDo kuaOmit = last.nextOmit(lottery.getPeriod(), lottery.getRed());
            omitList.add(kuaOmit);
            last = kuaOmit;
        }
        repository.saveBatch(omitList);
    }

    @Override
    public void nextOmit(String period, LotteryEnum type) {
        LotteryInfoPo lottery = mapper.getLotteryInfo(type.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        String         lastPeriod = type.lastPeriod(period);
        LottoKuaOmitDo sumOmitDo  = repository.ofPeriod(type, lastPeriod);
        LottoKuaOmitDo nextOmit   = sumOmitDo.nextOmit(period, lottery.getRed());
        repository.save(nextOmit);
    }

}
