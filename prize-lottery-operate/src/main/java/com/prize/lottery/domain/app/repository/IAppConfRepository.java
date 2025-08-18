package com.prize.lottery.domain.app.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.app.model.AppConfDo;

public interface IAppConfRepository {

    void save(Aggregate<Long, AppConfDo> aggregate);

    Aggregate<Long, AppConfDo> ofId(Long id);

}
