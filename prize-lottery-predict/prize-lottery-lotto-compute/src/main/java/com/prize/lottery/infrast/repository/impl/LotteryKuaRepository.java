package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LottoKuaOmitDo;
import com.prize.lottery.domain.omit.repository.ILotteryKuaRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryKuaOmitPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryKuaRepository implements ILotteryKuaRepository {

    private final LotteryInfoMapper  mapper;
    private final LottoOmitConverter converter;

    @Override
    public void save(LottoKuaOmitDo omit) {
        LotteryKuaOmitPo kuaOmit = converter.toPo(omit);
        mapper.addKuaOmits(Lists.newArrayList(kuaOmit));
    }

    @Override
    public void saveBatch(List<LottoKuaOmitDo> omits) {
        List<LotteryKuaOmitPo> kuaList = converter.toKuaList(omits);
        mapper.addKuaOmits(kuaList);
    }

    @Override
    public LottoKuaOmitDo latestOmit(LotteryEnum type) {
        List<LotteryKuaOmitPo> omitList = mapper.getKuaOmitList(type.getType(), 1);
        Assert.state(CollectionUtils.isNotEmpty(omitList), ResponseHandler.NO_LOTTERY_OMIT);
        return converter.toDo(omitList.get(0));
    }

    @Override
    public LottoKuaOmitDo ofPeriod(LotteryEnum type, String period) {
        LotteryKuaOmitPo kuaOmit = mapper.getLottoKuaOmit(type.getType(), period);
        Assert.notNull(kuaOmit, ResponseHandler.NO_LOTTERY_OMIT);
        return converter.toDo(kuaOmit);
    }

}
