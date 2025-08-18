package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.message.model.AnnounceInfoDo;
import com.prize.lottery.domain.message.model.AnnounceMailboxDo;
import com.prize.lottery.infrast.persist.po.AnnounceInfoPo;
import com.prize.lottery.infrast.persist.po.AnnounceMailboxPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnnounceConverter {

    AnnounceMailboxDo toDo(AnnounceMailboxPo mailbox);

    AnnounceMailboxPo toPo(AnnounceMailboxDo mailbox);

    AnnounceInfoPo toPo(AnnounceInfoDo announce);

    AnnounceInfoDo toDo(AnnounceInfoPo announce);

}
