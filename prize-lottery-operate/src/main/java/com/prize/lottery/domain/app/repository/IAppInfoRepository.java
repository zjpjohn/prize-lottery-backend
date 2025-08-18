package com.prize.lottery.domain.app.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.app.model.AppInfoDo;

public interface IAppInfoRepository {

    void save(Aggregate<Long, AppInfoDo> aggregate);

    Aggregate<Long, AppInfoDo> ofId(Long id);

    boolean existApp(String name);

    boolean existApp(Long appId);

    boolean existAppNo(String appNo);

    void issueAppMainVersion(String appNo,String mainVersion);

}
