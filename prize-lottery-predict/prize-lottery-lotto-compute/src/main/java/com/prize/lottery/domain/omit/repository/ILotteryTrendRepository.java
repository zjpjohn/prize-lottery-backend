package com.prize.lottery.domain.omit.repository;


import com.prize.lottery.domain.omit.model.LotteryTrendOmitDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;

public interface ILotteryTrendRepository {

    void save(LotteryTrendOmitDo omit);

    void saveBatch(List<LotteryTrendOmitDo> omits);

    LotteryTrendOmitDo latestOmit(LotteryEnum type);

    LotteryTrendOmitDo ofPeriod(LotteryEnum type, String period);

}
