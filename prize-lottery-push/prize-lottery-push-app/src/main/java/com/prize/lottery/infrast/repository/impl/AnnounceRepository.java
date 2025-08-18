package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.message.model.AnnounceInfoDo;
import com.prize.lottery.domain.message.model.AnnounceMailboxDo;
import com.prize.lottery.domain.message.repository.IAnnounceRepository;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.infrast.persist.mapper.AnnounceInfoMapper;
import com.prize.lottery.infrast.persist.po.AnnounceMailboxPo;
import com.prize.lottery.infrast.repository.converter.AnnounceConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnnounceRepository implements IAnnounceRepository {

    private final AnnounceInfoMapper mapper;
    private final AnnounceConverter  converter;

    @Override
    public void save(Aggregate<Long, AnnounceInfoDo> aggregate) {
        AnnounceInfoDo root = aggregate.getRoot();
        if (root.isNew()) {
            mapper.addAnnounceInfo(converter.toPo(root));
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editAnnounceInfo);
    }

    @Override
    public Aggregate<Long, AnnounceInfoDo> of(Long id) {
        return Optional.ofNullable(mapper.getAnnounceInfo(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseErrorHandler.ANNOUNCE_NOT_EXIST);
    }

    @Override
    public void save(AnnounceMailboxDo mailbox) {
        AnnounceMailboxPo announceMailbox = converter.toPo(mailbox);
        mapper.addAnnounceMailbox(Lists.newArrayList(announceMailbox));
    }

    @Override
    public AnnounceMailboxDo ofMailBox(Long receiverId, String channel) {
        return Optional.ofNullable(mapper.getAnnounceMailbox(receiverId, channel))
                       .map(converter::toDo)
                       .orElseGet(() -> new AnnounceMailboxDo(receiverId, channel));
    }
}
