package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.master.model.MasterBattle;
import com.prize.lottery.domain.master.repository.IMasterBattleRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.repository.converter.MasterBattleConverter;
import com.prize.lottery.mapper.MasterBattleMapper;
import com.prize.lottery.po.master.MasterBattlePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MasterBattleRepository implements IMasterBattleRepository {

    private final MasterBattleMapper    mapper;
    private final MasterBattleConverter converter;

    @Override
    public void save(MasterBattle battle) {
        MasterBattlePo battlePo = converter.toPo(battle);
        if (battlePo.getId() == null) {
            mapper.addMasterBattle(battlePo);
            battle.setId(battlePo.getId());
            return;
        }
        mapper.editMasterBattle(battlePo);
    }

    @Override
    public MasterBattle ofId(Long id) {
        return Optional.ofNullable(mapper.getMasterBattleById(id))
                       .map(converter::toDo)
                       .orElseThrow(ResponseHandler.MASTER_BATTLE_NONE);
    }

    @Override
    public MasterBattle ofUk(LotteryEnum type, Long userId, String masterId, String period) {
        MasterBattlePo masterBattle = mapper.getMasterBattleByUk(type.getType(), userId, masterId, period);
        return masterBattle != null ? converter.toDo(masterBattle) : null;
    }

}
