package com.prize.lottery.infrast.repository.impl;

import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LotteryCodeDo;
import com.prize.lottery.domain.omit.repository.ILotteryCodeRepository;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryCodePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryCodeRepository implements ILotteryCodeRepository {

    private final LotteryInfoMapper  mapper;
    private final LottoOmitConverter converter;

    @Override
    public void save(LotteryCodeDo code) {
        LotteryCodePo lotteryCode = converter.toPo(code);
        mapper.addLotteryCodes(Lists.newArrayList(lotteryCode));
    }

    @Override
    public void saveBatch(List<LotteryCodeDo> codes) {
        List<LotteryCodePo> lotteryCodes = converter.toCodeList(codes);
        mapper.addLotteryCodes(lotteryCodes);
    }

}
