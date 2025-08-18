package com.prize.lottery.domain.omit.repository;


import com.prize.lottery.domain.omit.model.LottoPianOmitDo;

import java.util.List;

public interface ILottoPianOmitRepository {

    void save(LottoPianOmitDo omit);

    void saveBatch(List<LottoPianOmitDo> omits);

}
