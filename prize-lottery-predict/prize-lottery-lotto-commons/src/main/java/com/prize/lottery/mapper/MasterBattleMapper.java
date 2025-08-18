package com.prize.lottery.mapper;

import com.prize.lottery.po.master.MasterBattlePo;
import com.prize.lottery.vo.MasterBattleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MasterBattleMapper {

    int addMasterBattle(MasterBattlePo masterBattle);

    int editMasterBattle(MasterBattlePo masterBattle);

    MasterBattlePo getMasterBattleById(Long id);

    MasterBattlePo getMasterBattleByUk(@Param("type") String type,
                                       @Param("userId") Long userId,
                                       @Param("masterId") String masterId,
                                       @Param("period") String period);

    int removeMasterBattle(Long id);

    List<MasterBattleVo> getUserMasterBattles(@Param("userId") Long userId,
                                              @Param("type") String type,
                                              @Param("period") String period);

}
