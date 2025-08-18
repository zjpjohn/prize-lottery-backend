package com.prize.lottery.domain.notify.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.notify.model.NotifyInfoDo;

import java.util.List;

public interface INotifyInfoRepository {

    void save(Aggregate<Long, NotifyInfoDo> aggregate);

    Aggregate<Long, NotifyInfoDo> ofId(Long id);

    List<Long> notifyIdList(Long appKey);

}
