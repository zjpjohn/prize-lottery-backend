package com.prize.lottery.application.query;


import com.prize.lottery.application.query.vo.ChargeConfVo;
import com.prize.lottery.infrast.persist.po.ChargeConfPo;

import java.util.List;

public interface IChargeConfQueryService {

    List<ChargeConfPo> queryConfList();

    List<ChargeConfVo> usingConfList();

}
