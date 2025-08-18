package com.prize.lottery.domain.lottery.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.lottery.model.LotteryAroundDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;
import java.util.Optional;

public interface IAroundRepository {

    void save(Aggregate<Long, LotteryAroundDo> aggregate);

    void saveBatch(List<LotteryAroundDo> aroundList);

    void remove(Long id);

    Optional<Aggregate<Long, LotteryAroundDo>> of(String period, LotteryEnum type);
}
