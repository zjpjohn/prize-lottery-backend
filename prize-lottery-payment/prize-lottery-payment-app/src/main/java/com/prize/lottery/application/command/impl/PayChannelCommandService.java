package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.PayChannelAssembler;
import com.prize.lottery.application.command.IPayChannelCommandService;
import com.prize.lottery.application.command.dto.PayChannelCreateCmd;
import com.prize.lottery.application.command.dto.PayChannelModifyCmd;
import com.prize.lottery.domain.transfer.model.aggregate.PayChannelDo;
import com.prize.lottery.domain.transfer.repository.IPayChannelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayChannelCommandService implements IPayChannelCommandService {

    private final IPayChannelRepository repository;
    private final PayChannelAssembler   assembler;

    @Override
    @Transactional
    public void createChannel(PayChannelCreateCmd command) {
        PayChannelDo payChannel = new PayChannelDo(command, assembler::toDo);
        AggregateFactory.create(payChannel).save(repository::save);
    }

    @Override
    @Transactional
    public void modifyChannel(PayChannelModifyCmd command) {
        repository.ofId(command.getId()).peek(root -> root.modify(command, assembler::toDo)).save(repository::save);
    }

}
