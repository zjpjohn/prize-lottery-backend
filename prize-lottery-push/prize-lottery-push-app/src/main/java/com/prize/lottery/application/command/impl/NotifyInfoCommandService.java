package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.NotifyInfoAssembler;
import com.prize.lottery.application.command.INotifyInfoCommandService;
import com.prize.lottery.application.command.dto.NotifyInfoCreateCmd;
import com.prize.lottery.application.command.dto.NotifyInfoModifyCmd;
import com.prize.lottery.domain.notify.model.NotifyInfoDo;
import com.prize.lottery.domain.notify.model.NotifyTagGroupDo;
import com.prize.lottery.domain.notify.repository.INotifyInfoRepository;
import com.prize.lottery.domain.notify.repository.ITagGroupRepository;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyInfoCommandService implements INotifyInfoCommandService {

    private final NotifyInfoAssembler   assembler;
    private final INotifyInfoRepository repository;
    private final ITagGroupRepository   groupRepository;

    @Override
    public void createNotifyInfo(NotifyInfoCreateCmd command) {
        command.checkValidate();
        Aggregate<Long, NotifyTagGroupDo> group    = groupRepository.ofId(command.getGroupId());
        NotifyTagGroupDo                  tagGroup = group.getRoot();
        Assert.state(tagGroup.canNotify(), ResponseErrorHandler.INVALID_TAG_GROUP);

        NotifyInfoDo notifyInfo = new NotifyInfoDo(tagGroup.getAppKey(), command, assembler::toDo);
        AggregateFactory.create(notifyInfo).save(repository::save);
    }

    @Override
    public void modifyNotifyInfo(NotifyInfoModifyCmd command) {
        repository.ofId(command.getId()).peek(root -> {
            command.checkOpenType(root.getOpenType());
            root.modify(command, assembler::toDo);
        }).save(repository::save);
    }

}
