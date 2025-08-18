package com.prize.lottery.domain.omit.repository;


import com.prize.lottery.domain.omit.model.LotteryItemOmitDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;

public interface ILotteryItemOmitRepository {

    void save(LotteryItemOmitDo omit);

    void saveBatch(List<LotteryItemOmitDo> omits);

    LotteryItemOmitDo latest(LotteryEnum type);

    LotteryItemOmitDo ofPeriod(LotteryEnum type, String period);

}
