package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.activity.model.ActivityUserDo;
import com.prize.lottery.domain.activity.repository.IActivityUserRepository;
import com.prize.lottery.infrast.persist.mapper.ActivityInfoMapper;
import com.prize.lottery.infrast.repository.converter.ActivityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ActivityUserRepository implements IActivityUserRepository {

    private final ActivityInfoMapper mapper;
    private final ActivityConverter  converter;

    @Override
    public void save(Aggregate<Long, ActivityUserDo> aggregate) {
        ActivityUserDo activityUser = aggregate.getRoot();
        if (activityUser.isNew()) {
            mapper.addActivityUser(converter.toPo(activityUser));
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editActivityUser);
    }

    @Override
    public Aggregate<Long, ActivityUserDo> of(Long activityId, Long userId) {
        ActivityUserDo activityUser = Optional.ofNullable(mapper.getActivityUser(activityId, userId))
                                              .map(converter::toDo)
                                              .orElseGet(() -> new ActivityUserDo(activityId, userId));
        return AggregateFactory.create(activityUser);
    }

}
