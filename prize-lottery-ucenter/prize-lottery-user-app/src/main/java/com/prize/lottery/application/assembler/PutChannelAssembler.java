package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.PutChannelCreateCmd;
import com.prize.lottery.application.command.dto.PutChannelEditCmd;
import com.prize.lottery.application.query.vo.PutChannelVo;
import com.prize.lottery.domain.channel.model.PutChannelDo;
import com.prize.lottery.infrast.persist.po.PutChannelPo;
import com.prize.lottery.infrast.persist.vo.PutChannelStatsVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PutChannelAssembler {

    void toDo(PutChannelCreateCmd command, @MappingTarget PutChannelDo channel);

    void toDo(PutChannelEditCmd command, @MappingTarget PutChannelDo channel);

    PutChannelVo toVo(PutChannelPo channel);

    List<PutChannelVo> toVoList(List<PutChannelPo> channels);

    void toVo(PutChannelStatsVo stats, @MappingTarget PutChannelVo channel);
}
