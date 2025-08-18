package com.prize.lottery.infrast.repository.impl;

import com.google.common.collect.Lists;
import com.prize.lottery.domain.message.model.RemindMailboxDo;
import com.prize.lottery.domain.message.repository.IRemindRepository;
import com.prize.lottery.infrast.persist.mapper.RemindInfoMapper;
import com.prize.lottery.infrast.persist.po.RemindMailBoxPo;
import com.prize.lottery.infrast.repository.converter.RemindConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemindRepository implements IRemindRepository {

    private final RemindInfoMapper mapper;
    private final RemindConverter  converter;

    @Override
    public void save(RemindMailboxDo mailbox) {
        RemindMailBoxPo remindMailBox = converter.toPo(mailbox);
        mapper.addRemindMailbox(Lists.newArrayList(remindMailBox));
    }

    @Override
    public RemindMailboxDo ofMailBox(Long receiverId, String channel) {
        return Optional.ofNullable(mapper.getRemindMailbox(receiverId, channel))
                       .map(converter::toDo)
                       .orElseGet(() -> new RemindMailboxDo(receiverId, channel));
    }
}
