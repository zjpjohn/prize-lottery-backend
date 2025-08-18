package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.app.model.AppFeedbackDo;
import com.prize.lottery.domain.app.repository.IFeedbackRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppFeedbackMapper;
import com.prize.lottery.infrast.persist.po.AppFeedbackPo;
import com.prize.lottery.infrast.repository.converter.AppFeedbackConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FeedbackRepository implements IFeedbackRepository {

    private final AppFeedbackMapper    mapper;
    private final AppFeedbackConverter converter;

    @Override
    public void save(Aggregate<Long, AppFeedbackDo> aggregate) {
        AppFeedbackDo root = aggregate.getRoot();
        if (root.isNew()) {
            AppFeedbackPo feedback = converter.toPo(root);
            mapper.addAppFeedback(feedback);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editAppFeedback);
    }

    @Override
    public Aggregate<Long, AppFeedbackDo> ofId(long id) {
        return Optional.ofNullable(mapper.getAppFeedback(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.FEEDBACK_NOT_EXIST);
    }
}
