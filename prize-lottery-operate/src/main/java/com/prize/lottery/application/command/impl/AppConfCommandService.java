package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.AppInfoAssembler;
import com.prize.lottery.application.command.IAppConfCommandService;
import com.prize.lottery.application.command.dto.ConfCreateCmd;
import com.prize.lottery.application.command.dto.ConfModifyCmd;
import com.prize.lottery.domain.app.model.AppConfDo;
import com.prize.lottery.domain.app.repository.IAppConfRepository;
import com.prize.lottery.domain.app.repository.IAppInfoRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppConfCommandService implements IAppConfCommandService {

    private final IAppInfoRepository appRepository;
    private final IAppConfRepository confRepository;
    private final AppInfoAssembler   assembler;

    @Override
    public void createAppConf(ConfCreateCmd command) {
        boolean existApp = appRepository.existAppNo(command.getAppNo());
        Assert.state(existApp, ResponseHandler.APP_NOT_EXIST);

        AggregateFactory.create(new AppConfDo(command, assembler::toDo)).save(confRepository::save);
    }

    @Override
    public void modifyAppConf(ConfModifyCmd command) {
        confRepository.ofId(command.getId())
                      .peek(root -> root.modify(command, assembler::toDo))
                      .save(confRepository::save);
    }

}
