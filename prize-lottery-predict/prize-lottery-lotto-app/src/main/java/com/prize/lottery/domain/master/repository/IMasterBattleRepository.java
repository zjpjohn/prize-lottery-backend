package com.prize.lottery.domain.master.repository;


import com.prize.lottery.domain.master.model.MasterBattle;
import com.prize.lottery.enums.LotteryEnum;

public interface IMasterBattleRepository {

    void save(MasterBattle battle);

    MasterBattle ofId(Long id);

    MasterBattle ofUk(LotteryEnum type, Long userId, String masterId, String period);

}
