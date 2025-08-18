package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.app.model.AppContactDo;
import com.prize.lottery.domain.app.repository.IAppContactRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppInfoMapper;
import com.prize.lottery.infrast.persist.po.AppContactPo;
import com.prize.lottery.infrast.repository.converter.AppInfoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AppContactRepository implements IAppContactRepository {

    private final AppInfoMapper    mapper;
    private final AppInfoConverter converter;

    @Override
    public void save(Aggregate<Long, AppContactDo> aggregate) {
        AppContactDo root = aggregate.getRoot();
        if (root.isNew()) {
            AppContactPo contact = converter.toPo(root);
            int          result  = mapper.addAppContact(contact);
            Assert.state(result > 0, ResponseHandler.DATA_SAVED_ERROR);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editAppContact);
    }

    @Override
    public Aggregate<Long, AppContactDo> ofId(Long id) {
        return mapper.getAppContact(id)
                     .map(converter::toDo)
                     .map(AggregateFactory::create)
                     .orElseThrow(ResponseHandler.CONTACT_NOT_EXIST);
    }

    @Override
    public void clearInvalid(String appNo) {
        mapper.delAppContacts(appNo);
    }
}
