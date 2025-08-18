package com.prize.lottery.domain.notify.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.notify.model.NotifyTagGroupDo;

public interface ITagGroupRepository {

    void save(Aggregate<Long, NotifyTagGroupDo> aggregate);

    Aggregate<Long, NotifyTagGroupDo> ofId(Long id);

    Boolean existTagGroup(Long groupId);

}
