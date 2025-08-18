package com.prize.lottery.domain.activity.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.activity.model.ActivityDrawDo;

import java.time.LocalDate;
import java.util.Optional;

public interface IActivityDrawRepository {

    void save(Aggregate<Long, ActivityDrawDo> aggregate);

    Optional<ActivityDrawDo> ofId(Long id);

    Optional<ActivityDrawDo> ofDay(Long userId, LocalDate day);

}
