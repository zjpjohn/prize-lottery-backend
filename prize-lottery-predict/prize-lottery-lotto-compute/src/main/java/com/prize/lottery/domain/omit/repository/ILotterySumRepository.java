package com.prize.lottery.domain.omit.repository;


import com.prize.lottery.domain.omit.model.LottoSumOmitDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;

public interface ILotterySumRepository {

    void save(LottoSumOmitDo omit);

    void saveBatch(List<LottoSumOmitDo> omits);

    LottoSumOmitDo latestOmit(LotteryEnum type);

    LottoSumOmitDo ofPeriod(LotteryEnum type, String period);

}
