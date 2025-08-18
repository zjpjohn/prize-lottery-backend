package com.prize.lottery.domain.app.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.app.model.FeedbackTypeDo;

public interface IFeedbackTypeRepository {

    void save(Aggregate<Long, FeedbackTypeDo> aggregate);

    Aggregate<Long, FeedbackTypeDo> ofId(Long id);

    Integer maxSort(String appNo);

    void remove(Long id);
}
