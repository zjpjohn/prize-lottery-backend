package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.omit.model.LotteryOttDo;
import com.prize.lottery.domain.omit.repository.ILotteryOttRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryOttPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryOttRepository implements ILotteryOttRepository {

    private final LotteryInfoMapper  mapper;
    private final LottoOmitConverter converter;

    @Override
    public void saveBatch(List<LotteryOttDo> ottList) {
        List<LotteryOttPo> ottPoList = converter.toOttPoList(ottList);
        mapper.addLotteryOtt(ottPoList);
    }

    @Override
    public LotteryOttDo of(LotteryEnum type, String period) {
        return Optional.ofNullable(mapper.getLotteryOtt(type, period)).map(converter::toDo).orElse(null);
    }

}
