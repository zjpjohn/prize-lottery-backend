package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.channel.model.PutRecordDo;
import com.prize.lottery.domain.channel.repository.IPutRecordRepository;
import com.prize.lottery.domain.channel.valobj.PutIncrOperation;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.PutChannelMapper;
import com.prize.lottery.infrast.persist.po.PutRecordPo;
import com.prize.lottery.infrast.repository.converter.PutChannelConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PutRecordRepository implements IPutRecordRepository {

    private final PutChannelMapper    mapper;
    private final PutChannelConverter converter;

    @Override
    public void save(Aggregate<Long, PutRecordDo> aggregate) {
        PutRecordDo root = aggregate.getRoot();
        if (root.isNew()) {
            PutRecordPo record = converter.toPo(root);
            int         result = mapper.addPutRecord(record);
            Assert.state(result > 0, ResponseHandler.DATA_SAVE_ERROR);
            return;
        }
        PutIncrOperation operation = root.getOperation();
        if (operation == null) {
            //编辑投放记录信息
            aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editPutRecord);
            return;
        }
        //投放记录邀请计数
        PutRecordPo putRecord = converter.toPo(root.getId(), operation.getValue());
        mapper.editPutRecord(putRecord);
    }

    @Override
    public Optional<Aggregate<Long, PutRecordDo>> ofId(Long id) {
        return Optional.ofNullable(mapper.getPutRecord(id)).map(converter::toDo).map(AggregateFactory::create);
    }

    @Override
    public Optional<Aggregate<Long, PutRecordDo>> ofCode(String code) {
        return Optional.ofNullable(mapper.getPutRecordByCode(code)).map(converter::toDo).map(AggregateFactory::create);
    }

}
