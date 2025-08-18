package com.prize.lottery.domain.omit.repository;


import com.prize.lottery.domain.omit.model.LotteryMatchOmitDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;

public interface ILotteryMatchRepository {

    void save(LotteryMatchOmitDo omit);

    void saveBatch(List<LotteryMatchOmitDo> omits);

    LotteryMatchOmitDo latestOmit(LotteryEnum type);

    LotteryMatchOmitDo ofPeriod(LotteryEnum type, String period);

}
