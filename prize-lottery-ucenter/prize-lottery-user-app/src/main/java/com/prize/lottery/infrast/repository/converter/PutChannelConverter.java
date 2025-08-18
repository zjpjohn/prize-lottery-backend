package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.channel.model.PutChannelDo;
import com.prize.lottery.domain.channel.model.PutRecordDo;
import com.prize.lottery.infrast.persist.po.PutChannelPo;
import com.prize.lottery.infrast.persist.po.PutRecordPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PutChannelConverter {

    PutChannelDo toDo(PutChannelPo channel);

    PutChannelPo toPo(PutChannelDo channel);

    PutRecordPo toPo(PutRecordDo record);

    PutRecordPo toPo(Long id, Integer userCnt);

    PutRecordDo toDo(PutRecordPo record);

}
