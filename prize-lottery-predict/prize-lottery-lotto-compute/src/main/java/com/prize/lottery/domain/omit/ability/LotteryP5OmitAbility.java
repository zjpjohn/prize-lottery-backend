package com.prize.lottery.domain.omit.ability;

import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LottoP5ItemOmitDo;
import com.prize.lottery.domain.omit.repository.ILotteryP5OmitRepository;
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
public class LotteryP5OmitAbility {

    private final LotteryInfoMapper        mapper;
    private final ILotteryP5OmitRepository repository;

    public void load() {
        LottoP5ItemOmitDo omit = LottoP5ItemOmitDo.load();
        repository.save(omit);
    }

    public void initialize() {
        LottoP5ItemOmitDo   latest    = repository.latest();
        List<LotteryInfoPo> lotteries = mapper.getLotteryInfoGtPeriod(LotteryEnum.PL5.getType(), latest.getPeriod());
        Assert.state(CollectionUtils.isNotEmpty(lotteries), ResponseHandler.NO_OPEN_LOTTERY);

        List<LottoP5ItemOmitDo> omitList = Lists.newArrayList();
        LottoP5ItemOmitDo       last     = latest;
        for (LotteryInfoPo lottery : lotteries) {
            LottoP5ItemOmitDo next = last.next(lottery.getPeriod(), lottery.getRed());
            omitList.add(next);
            last = next;
        }
        repository.saveBatch(omitList);
    }

    public void nextOmit(String period) {
        LotteryInfoPo lottery = mapper.getLotteryInfo(LotteryEnum.PL5.getType(), period);
        Assert.notNull(lottery, ResponseHandler.NO_OPEN_LOTTERY);

        String            lastPeriod = LotteryEnum.PL5.lastPeriod(period);
        LottoP5ItemOmitDo itemOmitDo = repository.ofPeriod(lastPeriod);
        LottoP5ItemOmitDo next       = itemOmitDo.next(period, lottery.getRed());
        repository.save(next);
    }

}
