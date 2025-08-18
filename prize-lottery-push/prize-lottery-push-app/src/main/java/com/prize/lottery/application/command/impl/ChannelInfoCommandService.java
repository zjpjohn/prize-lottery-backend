package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.ChannelAssembler;
import com.prize.lottery.application.command.IChannelInfoCommandService;
import com.prize.lottery.application.command.dto.ChannelCreateCmd;
import com.prize.lottery.application.command.dto.ChannelModifyCmd;
import com.prize.lottery.application.command.dto.MessageClearCmd;
import com.prize.lottery.domain.message.model.ChannelInfoDo;
import com.prize.lottery.domain.message.repository.IChannelInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelInfoCommandService implements IChannelInfoCommandService {

    private final ChannelAssembler       assembler;
    private final IChannelInfoRepository repository;

    @Override
    @Transactional
    public void createChannel(ChannelCreateCmd command) {
        ChannelInfoDo channel = new ChannelInfoDo(command, assembler::toDo);
        AggregateFactory.create(channel).save(repository::save);
    }

    @Override
    @Transactional
    public void editChannel(ChannelModifyCmd command) {
        repository.ofId(command.getId()).peek(root -> root.modify(command, assembler::toDo)).save(repository::save);
    }

    @Override
    @Transactional
    public void clearMessage(MessageClearCmd command) {

    }

}
