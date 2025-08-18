package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.app.model.FeedbackTypeDo;
import com.prize.lottery.domain.app.repository.IFeedbackTypeRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppFeedbackMapper;
import com.prize.lottery.infrast.persist.po.FeedbackTypePo;
import com.prize.lottery.infrast.repository.converter.AppFeedbackConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FeedbackTypeRepository implements IFeedbackTypeRepository {

    private final AppFeedbackMapper    mapper;
    private final AppFeedbackConverter converter;

    @Override
    public void save(Aggregate<Long, FeedbackTypeDo> aggregate) {
        FeedbackTypeDo root = aggregate.getRoot();
        if (root.isNew()) {
            FeedbackTypePo type   = converter.toPo(root);
            int            result = mapper.addFeedbackType(type);
            Assert.state(result > 0, ResponseHandler.DATA_SAVED_ERROR);
            return;
        }
        FeedbackTypeDo changed = aggregate.changed();
        if (changed != null) {
            if (changed.getSort() != null) {
                //反馈类型排序
                Integer oldSort = aggregate.getSnapshot().getSort();
                mapper.sortFeedbackType(root.getId(), oldSort, changed.getSort());
                return;
            }
            //反馈类型编辑
            FeedbackTypePo feedbackType = converter.toPo(changed);
            int            result       = mapper.editFeedbackType(feedbackType);
            Assert.state(result > 0, ResponseHandler.DATA_SAVED_ERROR);
        }
    }

    @Override
    public Aggregate<Long, FeedbackTypeDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getFeedbackType(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.FEEDBACK_TYPE_NOT_EXIST);
    }

    @Override
    public Integer maxSort(String appNo) {
        return mapper.typeMaxSort(appNo).orElse(0);
    }

    @Override
    public void remove(Long id) {
        int result = mapper.removeFeedType(id);
        Assert.state(result > 0, ResponseHandler.FEEDBACK_TYPE_NOT_EXIST);
    }
}
