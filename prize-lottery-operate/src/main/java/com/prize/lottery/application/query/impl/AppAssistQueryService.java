package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.AppAssistAssembler;
import com.prize.lottery.application.query.IAppAssistQueryService;
import com.prize.lottery.application.query.dto.AppAssistantQuery;
import com.prize.lottery.application.query.dto.AssistantListQuery;
import com.prize.lottery.application.vo.AppAssistantVo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppAssistantMapper;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppAssistantPo;
import com.prize.lottery.infrast.persist.valobj.AssistantApp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppAssistQueryService implements IAppAssistQueryService {

    private final AppAssistAssembler assistAssembler;
    private final AppAssistantMapper assistantMapper;
    private final AppInfoMapper      appInfoMapper;

    @Override
    public AssistantApp assistantApp(String appNo) {
        return appInfoMapper.getAssistantApp(appNo);
    }

    @Override
    public AppAssistantPo assistant(Long id) {
        return assistantMapper.getAppAssistant(id);
    }

    @Override
    public AppAssistantVo assistant(Long id, String appNo) {
        AppAssistantPo assistant = assistantMapper.getAppAssistant(id);
        Assert.notNull(assistant, ResponseHandler.ASSISTANT_NOT_EXIST);
        Assert.state(assistant.getAppNo().equals(appNo), ResponseHandler.INVALID_APP_NO);
        return assistAssembler.toVo(assistant);
    }

    @Override
    public Page<AppAssistantPo> listQuery(AssistantListQuery query) {
        return query.from().count(assistantMapper::countAppAssistants).query(assistantMapper::getAppAssistants);
    }

    @Override
    public List<AppAssistantVo> appAssistants(AppAssistantQuery query) {
        List<AppAssistantPo> assistants = assistantMapper.getUsingAppAssistants(query.getAppNo(), query.getVersion(), query.getType());
        if (CollectionUtils.isEmpty(assistants)) {
            return Collections.emptyList();
        }
        if (query.getDetail() != null && query.getDetail()) {
            return assistants.stream().map(assistAssembler::toVo).collect(Collectors.toList());
        }
        return assistants.stream().map(assistAssembler::toNoContentVo).collect(Collectors.toList());

    }

}
