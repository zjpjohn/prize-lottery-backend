package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.AppInfoAssembler;
import com.prize.lottery.application.command.IAppCommentCommandService;
import com.prize.lottery.application.command.dto.AppCommentBatchCmd;
import com.prize.lottery.application.command.dto.AppCommentCreateCmd;
import com.prize.lottery.application.command.dto.AppCommentEditCmd;
import com.prize.lottery.domain.app.model.AppCommentDo;
import com.prize.lottery.domain.app.repository.IAppCommentRepository;
import com.prize.lottery.domain.app.repository.IAppInfoRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppCommentCommandService implements IAppCommentCommandService {

    private final AppInfoAssembler      assembler;
    private final IAppInfoRepository    appRepository;
    private final IAppCommentRepository commentRepository;

    @Override
    @Transactional
    public void batchComments(AppCommentBatchCmd command) {
        boolean existed = appRepository.existApp(command.getAppNo());
        Assert.state(existed, ResponseHandler.APP_NOT_EXIST);
        commentRepository.saveJsonList(command.getAppNo(), command.getComments());
    }

    @Override
    @Transactional
    public void addAppComment(AppCommentCreateCmd command) {
        boolean existed = appRepository.existApp(command.getAppNo());
        Assert.state(existed, ResponseHandler.APP_NOT_EXIST);

        AppCommentDo commentDo = new AppCommentDo(command, assembler::toDo);
        AggregateFactory.create(commentDo).save(commentRepository::save);
    }

    @Override
    @Transactional
    public void editAppComment(AppCommentEditCmd command) {
        commentRepository.ofId(command.getId())
                         .peek(comment -> comment.modify(command, assembler::toDo))
                         .save(commentRepository::save);
    }

}
