package com.prize.lottery.infrast.repository.impl;

import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LottoPianOmitDo;
import com.prize.lottery.domain.omit.repository.ILottoPianOmitRepository;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LottoPianOmitPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LottoPianOmitRepository implements ILottoPianOmitRepository {

    private final LotteryInfoMapper  mapper;
    private final LottoOmitConverter converter;

    @Override
    public void save(LottoPianOmitDo omit) {
        LottoPianOmitPo pianOmit = converter.toPo(omit);
        mapper.addPianOmits(Lists.newArrayList(pianOmit));
    }

    @Override
    public void saveBatch(List<LottoPianOmitDo> omits) {
        List<LottoPianOmitPo> pianList = converter.toPianList(omits);
        mapper.addPianOmits(pianList);
    }

}
