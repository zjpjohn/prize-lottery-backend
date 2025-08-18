package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.channel.model.PutChannelDo;
import com.prize.lottery.domain.channel.repository.IPutChannelRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.PutChannelMapper;
import com.prize.lottery.infrast.persist.po.PutChannelPo;
import com.prize.lottery.infrast.repository.converter.PutChannelConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PutChannelRepository implements IPutChannelRepository {

    private final PutChannelMapper    mapper;
    private final PutChannelConverter converter;

    @Override
    public void save(Aggregate<Long, PutChannelDo> aggregate) {
        PutChannelDo root = aggregate.getRoot();
        if (root.isNew()) {
            PutChannelPo putChannel = converter.toPo(root);
            int          result     = mapper.addPutChannel(putChannel);
            Assert.state(result > 0, ResponseHandler.DATA_SAVE_ERROR);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editPutChannel);
    }

    @Override
    public Aggregate<Long, PutChannelDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getPutChannel(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.PUT_CHANNEL_NONE);
    }
}
