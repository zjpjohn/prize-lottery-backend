package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LotteryTrendOmitDo;
import com.prize.lottery.domain.omit.repository.ILotteryTrendRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryTrendOmitPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryTrendRepository implements ILotteryTrendRepository {

    private final LotteryInfoMapper  mapper;
    private final LottoOmitConverter converter;

    @Override
    public void save(LotteryTrendOmitDo omit) {
        LotteryTrendOmitPo trendOmit = converter.toPo(omit);
        mapper.addLotteryTrendOmits(Lists.newArrayList(trendOmit));
    }

    @Override
    public void saveBatch(List<LotteryTrendOmitDo> omits) {
        List<LotteryTrendOmitPo> trendList = converter.toTrendList(omits);
        mapper.addLotteryTrendOmits(trendList);
    }

    @Override
    public LotteryTrendOmitDo latestOmit(LotteryEnum type) {
        LotteryTrendOmitPo omit = mapper.getLatestTrendOmit(type);
        Assert.notNull(omit, ResponseHandler.NO_INIT_OMIT);
        return converter.toDo(omit);
    }

    @Override
    public LotteryTrendOmitDo ofPeriod(LotteryEnum type, String period) {
        LotteryTrendOmitPo trendOmit = mapper.getLotteryTrendOmit(type, period);
        Assert.notNull(trendOmit, ResponseHandler.NO_LOTTERY_OMIT);
        return converter.toDo(trendOmit);
    }

}
