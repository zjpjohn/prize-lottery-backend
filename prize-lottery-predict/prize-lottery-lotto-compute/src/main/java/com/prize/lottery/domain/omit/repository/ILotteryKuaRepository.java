package com.prize.lottery.domain.omit.repository;


import com.prize.lottery.domain.omit.model.LottoKuaOmitDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;

public interface ILotteryKuaRepository {

    void save(LottoKuaOmitDo omit);

    void saveBatch(List<LottoKuaOmitDo> omits);

    LottoKuaOmitDo latestOmit(LotteryEnum type);

    LottoKuaOmitDo ofPeriod(LotteryEnum type, String period);

}
