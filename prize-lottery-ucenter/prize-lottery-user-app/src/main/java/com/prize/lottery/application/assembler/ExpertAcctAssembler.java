package com.prize.lottery.application.assembler;

import com.prize.lottery.application.command.vo.ExpertAccountVo;
import com.prize.lottery.domain.expert.model.ExpertBalance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpertAcctAssembler {

    ExpertAccountVo toVo(ExpertBalance balance, String name, String phone);
}
