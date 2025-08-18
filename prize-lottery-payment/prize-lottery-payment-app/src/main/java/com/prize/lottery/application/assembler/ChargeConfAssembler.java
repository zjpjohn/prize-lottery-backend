package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.dto.ChargeConfCreateCmd;
import com.prize.lottery.application.command.dto.ChargeConfEditCmd;
import com.prize.lottery.application.query.vo.ChargeConfVo;
import com.prize.lottery.domain.order.model.aggregate.ChargeConfDo;
import com.prize.lottery.infrast.persist.po.ChargeConfPo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChargeConfAssembler {

    void toDo(ChargeConfCreateCmd command, @MappingTarget ChargeConfDo charge);

    void toDo(ChargeConfEditCmd command, @MappingTarget ChargeConfDo charge);

    ChargeConfVo toVo(ChargeConfPo charge);

    List<ChargeConfVo> toVoList(List<ChargeConfPo> confList);

}
