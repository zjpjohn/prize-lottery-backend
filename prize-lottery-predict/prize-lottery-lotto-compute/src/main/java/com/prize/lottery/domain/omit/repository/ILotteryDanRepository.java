package com.prize.lottery.domain.omit.repository;


import com.prize.lottery.domain.omit.model.LotteryDanDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;

public interface ILotteryDanRepository {

    void save(LotteryDanDo dan);

    void saveBatch(List<LotteryDanDo> danList);

    LotteryDanDo of(LotteryEnum type, String period);

    LotteryDanDo latest(LotteryEnum type);

}
