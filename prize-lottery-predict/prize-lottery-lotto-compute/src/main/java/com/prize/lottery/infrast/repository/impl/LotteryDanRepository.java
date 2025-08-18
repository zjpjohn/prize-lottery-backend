package com.prize.lottery.infrast.repository.impl;

import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LotteryDanDo;
import com.prize.lottery.domain.omit.repository.ILotteryDanRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryDanPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryDanRepository implements ILotteryDanRepository {

    private final LotteryInfoMapper  mapper;
    private final LottoOmitConverter converter;

    @Override
    public void save(LotteryDanDo dan) {
        LotteryDanPo lotteryDan = converter.toPo(dan);
        mapper.addLotteryDan(Lists.newArrayList(lotteryDan));
    }

    @Override
    public void saveBatch(List<LotteryDanDo> danList) {
        List<LotteryDanPo> danPoList = converter.toDanPoList(danList);
        mapper.addLotteryDan(danPoList);
    }

    @Override
    public LotteryDanDo of(LotteryEnum type, String period) {
        return Optional.ofNullable(mapper.getLotteryDan(type, period)).map(converter::toDo).orElse(null);
    }

    @Override
    public LotteryDanDo latest(LotteryEnum type) {
        return Optional.ofNullable(mapper.getLatestLotteryDan(type)).map(converter::toDo).orElse(null);
    }

}
