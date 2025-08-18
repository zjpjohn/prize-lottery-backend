package com.prize.lottery.domain.app.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.app.model.AppCommentDo;

public interface IAppCommentRepository {

    void save(Aggregate<Long, AppCommentDo> aggregate);

    Aggregate<Long, AppCommentDo> ofId(Long id);

    void saveJsonList(String appNo, String json);

}
