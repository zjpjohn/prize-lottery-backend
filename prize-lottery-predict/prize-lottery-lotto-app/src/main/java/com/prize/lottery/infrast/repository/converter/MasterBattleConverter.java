package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.master.model.MasterBattle;
import com.prize.lottery.po.master.MasterBattlePo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MasterBattleConverter {

    MasterBattle toDo(MasterBattlePo battle);

    MasterBattlePo toPo(MasterBattle battle);

}
