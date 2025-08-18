package com.prize.lottery.domain.app.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.app.model.AppResourceDo;
import com.prize.lottery.domain.app.valobj.AppResourceVal;
import com.prize.lottery.infrast.persist.po.AppResourcePo;

import java.util.List;


public interface IAppResourceRepository {

    void save(Aggregate<Long, AppResourceDo> aggregate);

    void batch(List<AppResourceVal> resources);

    Aggregate<Long, AppResourceDo> ofId(Long id);

    boolean existResourceNo(String appNo, String feNo);

    List<AppResourcePo> getUsingAppResources(String appNo);
}
