package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.lottery.model.LotteryFairTrialDo;
import com.prize.lottery.domain.lottery.repository.IFairTrialRepository;
import com.prize.lottery.infrast.repository.converter.LottoOmitConverter;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryFairTrialPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FairTrialRepository implements IFairTrialRepository {

    private final LotteryInfoMapper  mapper;
    private final LottoOmitConverter converter;

    @Override
    public void save(List<LotteryFairTrialDo> lotteries) {
        List<LotteryFairTrialPo> trialList = converter.toTrialList(lotteries);
        mapper.addLottoFairTrials(trialList);
    }

}
