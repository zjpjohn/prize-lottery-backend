package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.PayChannelCreateCmd;
import com.prize.lottery.application.command.dto.PayChannelModifyCmd;
import com.prize.lottery.application.query.vo.PayChannelVo;
import com.prize.lottery.domain.transfer.model.aggregate.PayChannelDo;
import com.prize.lottery.infrast.persist.po.PayChannelPo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PayChannelAssembler {

    void toDo(PayChannelCreateCmd command, @MappingTarget PayChannelDo channel);

    void toDo(PayChannelModifyCmd command, @MappingTarget PayChannelDo channel);

    PayChannelVo toVo(PayChannelPo channel);

    List<PayChannelVo> toVoList(List<PayChannelPo> channels);

}
