package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.NotifyAppAssembler;
import com.prize.lottery.application.command.INotifyAppCommandService;
import com.prize.lottery.application.command.dto.NotifyAppCreateCmd;
import com.prize.lottery.application.command.dto.NotifyAppModifyCmd;
import com.prize.lottery.domain.notify.model.NotifyAppDo;
import com.prize.lottery.domain.notify.repository.INotifyAppRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyAppCommandService implements INotifyAppCommandService {

    private final INotifyAppRepository repository;
    private final NotifyAppAssembler   assembler;

    @Override
    public void createNotifyApp(NotifyAppCreateCmd command) {
        NotifyAppDo appDo = new NotifyAppDo(command, assembler::toDo);
        AggregateFactory.create(appDo).save(repository::save);
    }

    @Override
    public void modifyNotifyApp(NotifyAppModifyCmd command) {
        repository.ofId(command.getId()).peek(root -> root.modify(command, assembler::toDo)).save(repository::save);
    }

}
