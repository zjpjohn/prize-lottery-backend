package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.AppInfoAssembler;
import com.prize.lottery.application.command.IAppInfoCommandService;
import com.prize.lottery.application.command.dto.*;
import com.prize.lottery.domain.app.model.AppContactDo;
import com.prize.lottery.domain.app.model.AppInfoDo;
import com.prize.lottery.domain.app.model.AppVersionDo;
import com.prize.lottery.domain.app.repository.IAppContactRepository;
import com.prize.lottery.domain.app.repository.IAppInfoRepository;
import com.prize.lottery.domain.app.repository.IAppVersionRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppInfoCommandService implements IAppInfoCommandService {

    private final AppInfoAssembler      appInfoAssembler;
    private final IAppInfoRepository    appInfoRepository;
    private final IAppVersionRepository appVersionRepository;
    private final IAppContactRepository appContactRepository;

    @Override
    @Transactional
    public void createAppInfo(AppInfoCreateCmd command) {
        boolean existed = appInfoRepository.existApp(command.getName());
        Assert.state(existed, ResponseHandler.APP_NAME_EXIST);

        AppInfoDo appInfoDo = new AppInfoDo(command, appInfoAssembler::toDo);
        AggregateFactory.create(appInfoDo).save(appInfoRepository::save);
    }

    @Override
    @Transactional
    public void editAppInfo(AppInfoModifyCmd command) {
        appInfoRepository.ofId(command.getId())
                         .peek(root -> root.modify(command, appInfoAssembler::toDo))
                         .save(appInfoRepository::save);
    }

    @Override
    @Transactional
    public void issueAppMainVersion(Long id) {
        appVersionRepository.ofId(id).peek(AppVersionDo::issueMainVersion).peek(root -> {
            appInfoRepository.issueAppMainVersion(root.getAppNo(), root.getAppVer());
        });
    }

    @Override
    @Transactional
    public void createAppVersion(VersionCreateCmd command) {
        boolean existApp = appInfoRepository.existApp(command.getAppNo());
        Assert.state(existApp, ResponseHandler.APP_NOT_EXIST);

        AppVersionDo appVersionDo = new AppVersionDo(command, appInfoAssembler::toDo);
        AggregateFactory.create(appVersionDo).save(appVersionRepository::save);
    }

    @Override
    @Transactional
    public void editAppVersion(VersionModifyCmd command) {
        appVersionRepository.ofId(command.getId())
                            .peek(root -> root.modify(command, appInfoAssembler::toDo))
                            .save(appVersionRepository::save);
    }

    @Override
    @Transactional
    public void onlineVersion(Long id) {
        appVersionRepository.ofId(id).peek(AppVersionDo::online).save(appVersionRepository::save);
    }

    @Override
    @Transactional
    public void offlineVersion(Long id) {
        appVersionRepository.ofId(id).peek(AppVersionDo::offline).save(appVersionRepository::save);
    }

    @Override
    public void createContact(ContactCreateCmd command) {
        AppContactDo contact = new AppContactDo(command, appInfoAssembler::toDo);
        AggregateFactory.create(contact).save(appContactRepository::save);
    }

    @Override
    public void editContact(ContactEditCmd command) {
        appContactRepository.ofId(command.getId())
                            .peek(root -> root.modify(command, appInfoAssembler::toDo))
                            .save(appContactRepository::save);
    }

    @Override
    public void removeContacts(String appNo) {
        appContactRepository.clearInvalid(appNo);
    }

}
