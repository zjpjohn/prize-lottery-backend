package com.prize.lottery.domain.notify.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.notify.model.NotifyAppDo;

import java.util.List;

public interface INotifyAppRepository {

    void save(Aggregate<Long, NotifyAppDo> aggregate);

    Aggregate<Long, NotifyAppDo> ofId(Long id);

    Long appKey(String appNo);

    boolean exist(Long appKey);

    List<Long> ofAllKeys();

}
