package com.prize.lottery.domain.app.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.app.model.AppVerifyDo;

public interface IAppVerifyRepository {

    void save(Aggregate<Long, AppVerifyDo> aggregate);

    Aggregate<Long, AppVerifyDo> ofId(Long Id);

}
