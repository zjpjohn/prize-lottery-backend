package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LotteryMatchOmitDo;
import com.prize.lottery.domain.omit.repository.ILotteryMatchRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryMatchOmitPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryMatchRepository implements ILotteryMatchRepository {

    private final LotteryInfoMapper  mapper;
    private final LottoOmitConverter converter;

    @Override
    public void save(LotteryMatchOmitDo omit) {
        LotteryMatchOmitPo omitPo = converter.toPo(omit);
        mapper.addLotteryMatchOmits(Lists.newArrayList(omitPo));
    }

    @Override
    public void saveBatch(List<LotteryMatchOmitDo> omits) {
        List<LotteryMatchOmitPo> poList = converter.toMatchList(omits);
        mapper.addLotteryMatchOmits(poList);
    }

    @Override
    public LotteryMatchOmitDo latestOmit(LotteryEnum type) {
        LotteryMatchOmitPo matchOmit = mapper.getLatestMatchOmit(type);
        Assert.notNull(matchOmit, ResponseHandler.NO_LOTTERY_OMIT);
        return converter.toDo(matchOmit);
    }

    @Override
    public LotteryMatchOmitDo ofPeriod(LotteryEnum type, String period) {
        LotteryMatchOmitPo matchOmit = mapper.getLotteryMatchOmit(type, period);
        Assert.notNull(matchOmit, ResponseHandler.NO_LOTTERY_OMIT);
        return converter.toDo(matchOmit);
    }

}
