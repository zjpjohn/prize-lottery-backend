package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.ChargeConfAssembler;
import com.prize.lottery.application.command.IChargeConfCommandService;
import com.prize.lottery.application.command.dto.ChargeConfCreateCmd;
import com.prize.lottery.application.command.dto.ChargeConfEditCmd;
import com.prize.lottery.domain.order.model.aggregate.ChargeConfDo;
import com.prize.lottery.infrast.repository.impl.ChargeConfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChargeConfCommandService implements IChargeConfCommandService {

    private final ChargeConfRepository repository;
    private final ChargeConfAssembler  assembler;

    @Override
    public void createConf(ChargeConfCreateCmd command) {
        ChargeConfDo chargeConf = new ChargeConfDo(command, assembler::toDo);
        AggregateFactory.create(chargeConf).save(repository::save);
    }

    @Override
    public void editConf(ChargeConfEditCmd command) {
        repository.ofId(command.getId()).peek(conf -> conf.modify(command, assembler::toDo)).save(repository::save);
    }

    @Override
    public void removeConf() {
        repository.clearConfig();
    }
}
