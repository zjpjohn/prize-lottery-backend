package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.activity.model.ActivityInfoDo;
import com.prize.lottery.domain.activity.repository.IActivityInfoRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.ActivityInfoMapper;
import com.prize.lottery.infrast.repository.converter.ActivityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActivityInfoRepository implements IActivityInfoRepository {

    private final ActivityInfoMapper mapper;
    private final ActivityConverter  converter;

    @Override
    public void save(Aggregate<Long, ActivityInfoDo> aggregate) {
        ActivityInfoDo root = aggregate.getRoot();
        if (root.isNew()) {
            mapper.addActivityInfo(converter.toPo(root));
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editActivityInfo);
    }

    @Override
    public Aggregate<Long, ActivityInfoDo> of(Long id) {
        return Optional.ofNullable(mapper.getActivityInfo(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.ACTIVITY_MEMBER_NONE);
    }

    @Override
    public Optional<Aggregate<Long, ActivityInfoDo>> ofUsing() {
        return mapper.getUsingActivity().map(converter::toDo).map(AggregateFactory::create);
    }

}
