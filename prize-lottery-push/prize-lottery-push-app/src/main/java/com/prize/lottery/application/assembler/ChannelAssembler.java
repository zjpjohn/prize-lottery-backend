package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.ChannelCreateCmd;
import com.prize.lottery.application.command.dto.ChannelModifyCmd;
import com.prize.lottery.application.query.vo.ChannelMessageVo;
import com.prize.lottery.application.query.vo.MessageInfoVo;
import com.prize.lottery.domain.message.model.ChannelInfoDo;
import com.prize.lottery.event.MessageEvent;
import com.prize.lottery.event.MessageType;
import com.prize.lottery.infrast.persist.po.AnnounceInfoPo;
import com.prize.lottery.infrast.persist.po.ChannelInfoPo;
import com.prize.lottery.infrast.persist.po.RemindInfoPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ChannelAssembler {

    void toDo(ChannelCreateCmd command, @MappingTarget ChannelInfoDo channel);

    void toDo(ChannelModifyCmd command, @MappingTarget ChannelInfoDo channel);

    ChannelMessageVo toVo(ChannelInfoPo channel);

    @Mapping(source = "type", target = "type")
    @Mapping(source = "event.action", target = "objAction")
    @Mapping(target = "mode", constant = "1")
    AnnounceInfoPo toAnnounce(MessageEvent event, MessageType type);

    @Mapping(source = "type", target = "type")
    @Mapping(source = "event.action", target = "objAction")
    RemindInfoPo toRemind(MessageEvent event, MessageType type);

    MessageInfoVo fromAnnounce(AnnounceInfoPo announce);

    MessageInfoVo fromRemind(RemindInfoPo remind);

}
