package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.pick.model.LotteryPickDo;
import com.prize.lottery.domain.pick.repository.ILotteryPickRepository;
import com.prize.lottery.infrast.repository.converter.LotteryPickConverter;
import com.prize.lottery.mapper.LotteryPickMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryPickRepository implements ILotteryPickRepository {

    private final LotteryPickMapper    mapper;
    private final LotteryPickConverter converter;

    @Override
    public void save(LotteryPickDo pick) {
        mapper.addLotteryPick(converter.toPo(pick));
    }

}
