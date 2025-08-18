package com.prize.lottery.domain.omit.repository;


import com.prize.lottery.domain.omit.model.LotteryOttDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;

public interface ILotteryOttRepository {

    void saveBatch(List<LotteryOttDo> ottList);

    LotteryOttDo of(LotteryEnum type, String period);

}
