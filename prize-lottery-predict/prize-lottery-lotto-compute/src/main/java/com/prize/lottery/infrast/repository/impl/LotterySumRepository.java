package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LottoSumOmitDo;
import com.prize.lottery.domain.omit.repository.ILotterySumRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotterySumOmitPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotterySumRepository implements ILotterySumRepository {

    private final LotteryInfoMapper  mapper;
    private final LottoOmitConverter converter;

    @Override
    public void save(LottoSumOmitDo omit) {
        LotterySumOmitPo sumOmit = converter.toPo(omit);
        mapper.addSumOmits(Lists.newArrayList(sumOmit));
    }

    @Override
    public void saveBatch(List<LottoSumOmitDo> omits) {
        List<LotterySumOmitPo> sumOmitList = converter.toSumList(omits);
        mapper.addSumOmits(sumOmitList);
    }

    @Override
    public LottoSumOmitDo latestOmit(LotteryEnum type) {
        List<LotterySumOmitPo> omitList = mapper.getSumOmitList(type.getType(), 1);
        Assert.state(CollectionUtils.isNotEmpty(omitList), ResponseHandler.NO_LOTTERY_OMIT);
        return converter.toDo(omitList.get(0));
    }

    @Override
    public LottoSumOmitDo ofPeriod(LotteryEnum type, String period) {
        LotterySumOmitPo sumOmit = mapper.getLottoSumOmit(type.getType(), period);
        Assert.notNull(sumOmit, ResponseHandler.NO_LOTTERY_OMIT);
        return converter.toDo(sumOmit);
    }

}
