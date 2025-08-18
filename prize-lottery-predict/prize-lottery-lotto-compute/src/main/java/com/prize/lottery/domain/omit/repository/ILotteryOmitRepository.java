package com.prize.lottery.domain.omit.repository;


import com.prize.lottery.domain.omit.model.LotteryOmitDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;
import java.util.Optional;

public interface ILotteryOmitRepository {

    void save(LotteryOmitDo omit);

    void saveBatch(List<LotteryOmitDo> omits);

    Optional<LotteryOmitDo> latestOmit(LotteryEnum type);

    LotteryOmitDo ofPeriod(LotteryEnum type, String period);

}
