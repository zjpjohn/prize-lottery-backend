package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.message.model.RemindMailboxDo;
import com.prize.lottery.infrast.persist.po.RemindMailBoxPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RemindConverter {

    RemindMailBoxPo toPo(RemindMailboxDo mailbox);

    RemindMailboxDo toDo(RemindMailBoxPo mailBox);

}
