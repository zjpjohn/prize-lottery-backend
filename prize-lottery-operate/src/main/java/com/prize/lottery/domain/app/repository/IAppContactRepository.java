package com.prize.lottery.domain.app.repository;

import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.app.model.AppContactDo;

public interface IAppContactRepository {

    void save(Aggregate<Long, AppContactDo> aggregate);

    Aggregate<Long, AppContactDo> ofId(Long id);

    void clearInvalid(String appNo);

}
