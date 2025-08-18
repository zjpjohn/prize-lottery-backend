package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.app.model.AppAssistantDo;
import com.prize.lottery.domain.app.repository.IAppAssistantRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppAssistantMapper;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppAssistantPo;
import com.prize.lottery.infrast.persist.valobj.AssistantApp;
import com.prize.lottery.infrast.repository.converter.AppAssistantConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AppAssistantRepository implements IAppAssistantRepository {

    private final AppInfoMapper         appMapper;
    private final AppAssistantMapper    assistantMapper;
    private final AppAssistantConverter converter;

    @Override
    public void save(Aggregate<Long, AppAssistantDo> aggregate) {
        AppAssistantDo root = aggregate.getRoot();
        if (root.isNew()) {
            AppAssistantPo assistant = converter.toPo(root);
            int            result    = assistantMapper.addAppAssistant(assistant);
            Assert.state(result > 0, ResponseHandler.DATA_SAVED_ERROR);
            return;
        }
        AppAssistantDo changed = aggregate.changed();
        if (changed == null) {
            return;
        }
        //助手排序情况
        if (changed.getSort() != null) {
            Integer sort = aggregate.getSnapshot().getSort();
            assistantMapper.sortAppAssistant(root.getId(), sort, changed.getSort());
            return;
        }
        //助手内容更新
        AppAssistantPo assistant = converter.toPo(changed);
        int            result    = assistantMapper.editAppAssistant(assistant);
        Assert.state(result > 0, ResponseHandler.DATA_SAVED_ERROR);
    }

    @Override
    public Aggregate<Long, AppAssistantDo> ofId(Long id) {
        return Optional.ofNullable(assistantMapper.getAppAssistant(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.ASSISTANT_NOT_EXIST);
    }

    @Override
    public Integer latestSort(String appNo) {
        return Optional.ofNullable(assistantMapper.getAssistantMaxSort(appNo)).orElse(0);
    }

    @Override
    public AssistantApp ofApp(String appNo) {
        return Optional.ofNullable(appMapper.getAssistantApp(appNo)).orElseThrow(ResponseHandler.APP_NOT_EXIST);
    }

}
