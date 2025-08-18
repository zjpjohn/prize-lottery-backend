package com.prize.lottery.infrast.persist.mapper;

import com.prize.lottery.infrast.persist.po.ChargeConfPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeConfMapper {

    int addChargeConf(ChargeConfPo conf);

    int editChargeConf(ChargeConfPo conf);

    int removeInvalidConf();

    ChargeConfPo getChargeConf(Long id);

    List<ChargeConfPo> getAllConfigList(@Param("state") Integer state);

}
