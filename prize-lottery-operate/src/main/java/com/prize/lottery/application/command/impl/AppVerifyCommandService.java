package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.AppInfoAssembler;
import com.prize.lottery.application.command.IAppVerifyCommandService;
import com.prize.lottery.application.command.dto.AppVerifyCreateCmd;
import com.prize.lottery.application.command.dto.AppVerifyModifyCmd;
import com.prize.lottery.domain.app.model.AppVerifyDo;
import com.prize.lottery.domain.app.repository.IAppVerifyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppVerifyCommandService implements IAppVerifyCommandService {

    private final AppInfoAssembler     assembler;
    private final IAppVerifyRepository repository;

    @Override
    public void createVerify(AppVerifyCreateCmd command) {
        AppVerifyDo appVerify = new AppVerifyDo(command, assembler::toDo);
        AggregateFactory.create(appVerify).save(repository::save);
    }

    @Override
    public void modifyVerify(AppVerifyModifyCmd command) {
        repository.ofId(command.getId()).peek(root -> root.modify(command, assembler::toDo)).save(repository::save);
    }

}
