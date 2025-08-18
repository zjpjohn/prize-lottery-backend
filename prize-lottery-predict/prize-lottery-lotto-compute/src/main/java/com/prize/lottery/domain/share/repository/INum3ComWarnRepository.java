package com.prize.lottery.domain.share.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.share.model.Num3ComWarnDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.Optional;

public interface INum3ComWarnRepository {

    void save(Aggregate<Long, Num3ComWarnDo> aggregate);

    Optional<Aggregate<Long, Num3ComWarnDo>> ofId(Long id);

    Optional<Aggregate<Long, Num3ComWarnDo>> ofUk(LotteryEnum type, String period);

}
