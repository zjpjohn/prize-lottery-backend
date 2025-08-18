package com.prize.lottery.domain.lottery.repository;


import com.prize.lottery.domain.lottery.model.LotteryFairTrialDo;

import java.util.List;

public interface IFairTrialRepository {

    void save(List<LotteryFairTrialDo> lotteries);

}
