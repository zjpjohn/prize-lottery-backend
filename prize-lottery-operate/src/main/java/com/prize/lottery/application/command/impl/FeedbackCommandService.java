package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.FeedbackAssembler;
import com.prize.lottery.application.command.IFeedbackCommandService;
import com.prize.lottery.application.command.dto.FeedbackCreateCmd;
import com.prize.lottery.application.command.dto.FeedbackHandleCmd;
import com.prize.lottery.domain.app.model.AppFeedbackDo;
import com.prize.lottery.domain.app.repository.IFeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackCommandService implements IFeedbackCommandService {

    private final FeedbackAssembler   assembler;
    private final IFeedbackRepository repository;

    @Override
    public void createFeedback(FeedbackCreateCmd command) {
        AppFeedbackDo feedback = assembler.toDO(command);
        repository.save(AggregateFactory.create(feedback));
    }

    @Override
    public void handleFeedback(FeedbackHandleCmd command) {
        Aggregate<Long, AppFeedbackDo> aggregate = repository.ofId(command.getId());
        aggregate.peek(v -> v.handle(command.getState(), command.getRemark())).save(repository::save);
    }

}
