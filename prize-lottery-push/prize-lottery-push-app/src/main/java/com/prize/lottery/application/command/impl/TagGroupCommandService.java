package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.NotifyTagAssembler;
import com.prize.lottery.application.command.ITagGroupCommandService;
import com.prize.lottery.application.command.dto.TagGroupCreateCmd;
import com.prize.lottery.application.command.dto.TagGroupModifyCmd;
import com.prize.lottery.domain.notify.model.NotifyTagGroupDo;
import com.prize.lottery.domain.notify.repository.INotifyAppRepository;
import com.prize.lottery.domain.notify.repository.ITagGroupRepository;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagGroupCommandService implements ITagGroupCommandService {

    private final NotifyTagAssembler   assembler;
    private final ITagGroupRepository  repository;
    private final INotifyAppRepository appRepository;

    @Override
    public void createTagGroup(TagGroupCreateCmd command) {
        boolean exist = appRepository.exist(command.getAppKey());
        Assert.state(exist, ResponseErrorHandler.NOTIFY_APP_NONE);

        NotifyTagGroupDo tagGroup = new NotifyTagGroupDo(command, assembler::toDo);
        AggregateFactory.create(tagGroup).save(repository::save);
    }

    @Override
    public void modifyTagGroup(TagGroupModifyCmd command) {
        repository.ofId(command.getId()).peek(root -> root.modify(command, assembler::toDo)).save(repository::save);
    }

    @Override
    public void dilatateTagGroup(Long groupId) {
        repository.ofId(groupId).peek(NotifyTagGroupDo::dilatateTags).save(repository::save);
    }

}
