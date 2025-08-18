package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LotteryItemOmitDo;
import com.prize.lottery.domain.omit.repository.ILotteryItemOmitRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryItemOmitPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryItemOmitRepository implements ILotteryItemOmitRepository {

    private final LotteryInfoMapper  mapper;
    private final LottoOmitConverter converter;

    @Override
    public void save(LotteryItemOmitDo omit) {
        LotteryItemOmitPo itemOmit = converter.toPo(omit);
        mapper.addLotteryItemOmits(Lists.newArrayList(itemOmit));
    }

    @Override
    public void saveBatch(List<LotteryItemOmitDo> omits) {
        List<LotteryItemOmitPo> itemList = converter.toItemList(omits);
        mapper.addLotteryItemOmits(itemList);
    }

    @Override
    public LotteryItemOmitDo latest(LotteryEnum type) {
        LotteryItemOmitPo itemOmit = mapper.getLatestLotteryItemOmit(type);
        Assert.notNull(itemOmit, ResponseHandler.NO_LOTTERY_OMIT);
        return converter.toDo(itemOmit);
    }

    @Override
    public LotteryItemOmitDo ofPeriod(LotteryEnum type, String period) {
        LotteryItemOmitPo itemOmit = mapper.getLotteryItemOmit(type, period);
        Assert.notNull(itemOmit, ResponseHandler.NO_LOTTERY_OMIT);
        return converter.toDo(itemOmit);
    }

}
