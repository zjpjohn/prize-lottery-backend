package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.message.model.ChannelInfoDo;
import com.prize.lottery.domain.message.repository.IChannelInfoRepository;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.infrast.persist.mapper.ChannelInfoMapper;
import com.prize.lottery.infrast.persist.po.ChannelInfoPo;
import com.prize.lottery.infrast.repository.converter.ChannelInfoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelInfoRepository implements IChannelInfoRepository {

    private final ChannelInfoMapper    mapper;
    private final ChannelInfoConverter converter;

    @Override
    public void save(Aggregate<Long, ChannelInfoDo> aggregate) {
        ChannelInfoDo root = aggregate.getRoot();
        if (root.isNew()) {
            ChannelInfoPo channel = converter.toPo(root);
            int           result  = mapper.addChannelInfo(channel);
            Assert.state(result > 0, ResponseErrorHandler.DATA_SAVE_ERROR);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editChannelInfo);
    }

    @Override
    public Aggregate<Long, ChannelInfoDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getChannelById(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseErrorHandler.CHANNEL_NOT_EXIST);
    }

    @Override
    public ChannelInfoDo ofUk(String channel) {
        return Optional.ofNullable(mapper.getChannelByUk(channel))
                       .map(converter::toDo)
                       .orElseThrow(ResponseErrorHandler.CHANNEL_NOT_EXIST);
    }

}
