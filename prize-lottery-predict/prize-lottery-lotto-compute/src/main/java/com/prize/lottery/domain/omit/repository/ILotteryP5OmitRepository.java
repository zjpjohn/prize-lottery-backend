package com.prize.lottery.domain.omit.repository;


import com.prize.lottery.domain.omit.model.LottoP5ItemOmitDo;

import java.util.List;

public interface ILotteryP5OmitRepository {

    void save(LottoP5ItemOmitDo omit);

    void saveBatch(List<LottoP5ItemOmitDo> omits);

    LottoP5ItemOmitDo latest();

    LottoP5ItemOmitDo ofPeriod(String period);

}
