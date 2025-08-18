package com.prize.lottery.domain.lottery.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.lottery.model.LotteryHoneyDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;
import java.util.Optional;

public interface IHoneyRepository {

    void save(Aggregate<Long, LotteryHoneyDo> aggregate);

    void saveBatch(List<LotteryHoneyDo> honeyList);

    void remove(Long id);

    Optional<Aggregate<Long, LotteryHoneyDo>> of(String period, LotteryEnum type);

}
