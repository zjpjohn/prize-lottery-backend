package com.prize.lottery.domain.app.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.app.model.AppFeedbackDo;

public interface IFeedbackRepository {

    void save(Aggregate<Long, AppFeedbackDo> aggregate);

    Aggregate<Long, AppFeedbackDo> ofId(long id);

}
