package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.AppAssistAssembler;
import com.prize.lottery.application.command.IAppAssistCommandService;
import com.prize.lottery.application.command.dto.AssistantCreateCmd;
import com.prize.lottery.application.command.dto.AssistantModifyCmd;
import com.prize.lottery.domain.app.model.AppAssistantDo;
import com.prize.lottery.domain.app.repository.IAppAssistantRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.valobj.AssistantApp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppAssistCommandService implements IAppAssistCommandService {

    private final AppAssistAssembler      assembler;
    private final IAppAssistantRepository repository;

    @Override
    public void createAssistant(AssistantCreateCmd command) {
        //助手应用已经使用版本校验
        AssistantApp app     = repository.ofApp(command.getAppNo());
        boolean      checked = app.checkVersion(command.getSuitVer());
        Assert.state(checked, ResponseHandler.INVALID_APP_VERSION);

        //当前排序版本
        Integer latestSort  = repository.latestSort(command.getAppNo());
        Integer currentSort = latestSort + 1;

        //保存应用助手
        AppAssistantDo assistantDo = new AppAssistantDo(command, currentSort, assembler::toDo);
        AggregateFactory.create(assistantDo).save(repository::save);
    }

    @Override
    public void modifyAssistant(AssistantModifyCmd command) {
        repository.ofId(command.getId()).peek(root -> {
            if (command.getState() == null && root.isSuitableVersion(command.getSuitVer())) {
                //当修改助手适用版本时校验版本
                AssistantApp app     = repository.ofApp(root.getAppNo());
                boolean      checked = app.checkVersion(command.getSuitVer());
                Assert.state(checked, ResponseHandler.INVALID_APP_VERSION);
            }
        }).peek(root -> root.modify(command, assembler::toDo)).save(repository::save);
    }

    @Override
    public void sortAssistant(Long id, Integer position) {
        repository.ofId(id).peek(root -> root.sort(position, repository::latestSort)).save(repository::save);
    }

}
