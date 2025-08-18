package com.prize.lottery.domain.app.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.app.model.AppVersionDo;

public interface IAppVersionRepository {

    void save(Aggregate<Long, AppVersionDo> aggregate);

    Aggregate<Long, AppVersionDo> ofId(Long id);

}
