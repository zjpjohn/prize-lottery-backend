package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.transfer.model.aggregate.PayChannelDo;
import com.prize.lottery.domain.transfer.repository.IPayChannelRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.PayChannelMapper;
import com.prize.lottery.infrast.persist.po.PayChannelPo;
import com.prize.lottery.infrast.repository.converter.PayChannelConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PayChannelRepository implements IPayChannelRepository {

    private final PayChannelMapper    mapper;
    private final PayChannelConverter converter;

    @Override
    public void save(Aggregate<Long, PayChannelDo> aggregate) {
        PayChannelDo root = aggregate.getRoot();
        if (root.isNew()) {
            PayChannelPo payChannel = converter.toPo(root);
            int          result     = mapper.addPayChannel(payChannel);
            Assert.state(result > 0, ResponseHandler.PAY_CHANNEL_EXIST);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editPayChannel);
    }

    @Override
    public Aggregate<Long, PayChannelDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getPayChannelById(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.PAY_CHANNEL_NONE);
    }

    @Override
    public PayChannelDo ofChannel(String channel) {
        return Optional.ofNullable(mapper.getPayChannel(channel)).map(converter::toDo).orElse(null);
    }

}
