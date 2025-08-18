package com.prize.lottery.application.assembler;

import com.prize.lottery.domain.master.model.MasterBattle;
import com.prize.lottery.value.MasterValue;
import com.prize.lottery.vo.MasterBattleVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MasterBattleAssembler {

    MasterBattleVo toVo(MasterBattle battle, MasterValue master, Object forecast);

}
