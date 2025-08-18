package com.prize.lottery.domain.activity.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.activity.model.ActivityInfoDo;

import java.util.Optional;

public interface IActivityInfoRepository {

    void save(Aggregate<Long, ActivityInfoDo> aggregate);

    Aggregate<Long, ActivityInfoDo> of(Long id);

    Optional<Aggregate<Long, ActivityInfoDo>> ofUsing();

}
