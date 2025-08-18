package com.prize.lottery.domain.omit.repository;


import com.prize.lottery.domain.omit.model.LotteryCodeDo;

import java.util.List;

public interface ILotteryCodeRepository {

    void save(LotteryCodeDo code);

    void saveBatch(List<LotteryCodeDo> codes);

}
