package com.prize.lottery.domain.activity.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.activity.model.ActivityUserDo;

public interface IActivityUserRepository {

    void save(Aggregate<Long, ActivityUserDo> aggregate);

    Aggregate<Long, ActivityUserDo> of(Long activityId, Long userId);

}
