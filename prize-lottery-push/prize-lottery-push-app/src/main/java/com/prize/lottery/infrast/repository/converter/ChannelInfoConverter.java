package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.message.model.ChannelInfoDo;
import com.prize.lottery.infrast.persist.po.ChannelInfoPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelInfoConverter {

    ChannelInfoDo toDo(ChannelInfoPo channel);

    ChannelInfoPo toPo(ChannelInfoDo channel);

}
