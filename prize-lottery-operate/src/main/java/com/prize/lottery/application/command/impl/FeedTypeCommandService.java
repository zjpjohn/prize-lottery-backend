package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.IFeedTypeCommandService;
import com.prize.lottery.application.command.dto.FeedTypeCreateCmd;
import com.prize.lottery.application.command.dto.FeedTypeEditCmd;
import com.prize.lottery.domain.app.model.FeedbackTypeDo;
import com.prize.lottery.domain.app.repository.IFeedbackTypeRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedTypeCommandService implements IFeedTypeCommandService {

    private final IFeedbackTypeRepository repository;
    private final AppInfoMapper           appInfoMapper;

    @Override
    public void createFeedType(FeedTypeCreateCmd command) {
        AppInfoPo appInfo = appInfoMapper.getAppInfo(command.getAppNo());
        Assert.notNull(appInfo, ResponseHandler.APP_NOT_EXIST);
        Integer        sort         = repository.maxSort(command.getAppNo()) + 1;
        FeedbackTypeDo feedbackType = new FeedbackTypeDo(command.getAppNo(), command.getType(), command.getSuitVer(), command.getRemark(), sort);
        repository.save(AggregateFactory.create(feedbackType));
    }

    @Override
    public void editFeedType(FeedTypeEditCmd command) {
        repository.ofId(command.getId())
                  .peek(type -> type.modify(command.getType(), command.getSuitVer(), command.getRemark()))
                  .save(repository::save);
    }

    @Override
    public void sortFeedType(Long id, Integer position) {
        repository.ofId(id).peek(root -> root.sort(position, repository::maxSort)).save(repository::save);
    }

    @Override
    public void removeFeedType(Long id) {
        repository.remove(id);
    }

}
