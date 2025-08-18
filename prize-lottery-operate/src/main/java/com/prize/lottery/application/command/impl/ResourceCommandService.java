package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.AppResourceAssembler;
import com.prize.lottery.application.command.IResourceCommandService;
import com.prize.lottery.application.command.dto.ResourceBatchCmd;
import com.prize.lottery.application.command.dto.ResourceCreateCmd;
import com.prize.lottery.application.command.dto.ResourceEditCmd;
import com.prize.lottery.domain.app.ability.AppResourceDomainService;
import com.prize.lottery.domain.app.model.AppResourceDo;
import com.prize.lottery.domain.app.repository.IAppInfoRepository;
import com.prize.lottery.domain.app.repository.IAppResourceRepository;
import com.prize.lottery.domain.app.valobj.AppResourceVal;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceCommandService implements IResourceCommandService {

    private final AppResourceDomainService resourceDomainService;
    private final IAppInfoRepository       appInfoRepository;
    private final IAppResourceRepository   appResourceRepository;
    private final AppResourceAssembler     appResourceAssembler;

    @Override
    @Transactional
    public void batchAddResources(ResourceBatchCmd cmd) {
        String  appNo   = cmd.getAppNo();
        boolean existed = appInfoRepository.existAppNo(appNo);
        Assert.state(existed, ResponseHandler.APP_NOT_EXIST);

        List<AppResourceVal> resourceList = cmd.getResources()
                                               .stream()
                                               .map(item -> item.convert(appNo))
                                               .collect(Collectors.toList());
        appResourceRepository.batch(resourceList);

    }

    @Override
    @Transactional
    public void createAppResource(ResourceCreateCmd cmd) {
        boolean exited = appResourceRepository.existResourceNo(cmd.getAppNo(), cmd.getFeNo());
        Assert.state(!exited, ResponseHandler.APP_RESOURCE_EXIST);
        AppResourceDo resource = new AppResourceDo(appResourceAssembler.toVal(cmd));
        AggregateFactory.create(resource).save(appResourceRepository::save);
    }

    @Override
    @Transactional
    public void editAppResource(ResourceEditCmd cmd) {
        AppResourceVal resourceVal = appResourceAssembler.toVal(cmd);
        resourceDomainService.modify(cmd.getId(), resourceVal);
    }

    @Override
    @Transactional
    public void useAppResource(Long id) {
        resourceDomainService.useResource(id);
    }

    @Override
    @Transactional
    public void unUseResource(Long id) {
        resourceDomainService.unUseResource(id);
    }

    @Override
    @Transactional
    public void removeResource(Long id) {
        appResourceRepository.ofId(id).peek(AppResourceDo::invalidResource).save(appResourceRepository::save);
    }

    @Override
    public void rollbackResource(Long id) {
        resourceDomainService.rollbackResource(id);
    }

    @Override
    public void loadResources(String appNo) {
        resourceDomainService.loadResources(appNo);
    }

}
