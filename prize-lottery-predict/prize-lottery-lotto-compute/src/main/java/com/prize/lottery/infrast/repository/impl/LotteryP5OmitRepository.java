package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LottoP5ItemOmitDo;
import com.prize.lottery.domain.omit.repository.ILotteryP5OmitRepository;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryPl5OmitPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LotteryP5OmitRepository implements ILotteryP5OmitRepository {

    private final LotteryInfoMapper  mapper;
    private final LottoOmitConverter converter;

    @Override
    public void save(LottoP5ItemOmitDo omit) {
        LotteryPl5OmitPo pl5Omit = converter.toPo(omit);
        mapper.addLotteryPl5Omits(Lists.newArrayList(pl5Omit));
    }

    @Override
    public void saveBatch(List<LottoP5ItemOmitDo> omits) {
        List<LotteryPl5OmitPo> p5List = converter.toP5List(omits);
        mapper.addLotteryPl5Omits(p5List);
    }

    @Override
    public LottoP5ItemOmitDo latest() {
        LotteryPl5OmitPo omit = mapper.getLatestLotteryPl5Omit();
        Assert.notNull(omit, ResponseHandler.NO_INIT_OMIT);
        return converter.toDo(omit);
    }

    @Override
    public LottoP5ItemOmitDo ofPeriod(String period) {
        LotteryPl5OmitPo omit = mapper.getLotteryPl5Omit(period);
        Assert.notNull(omit, ResponseHandler.NO_INIT_OMIT);
        return converter.toDo(omit);
    }

}
