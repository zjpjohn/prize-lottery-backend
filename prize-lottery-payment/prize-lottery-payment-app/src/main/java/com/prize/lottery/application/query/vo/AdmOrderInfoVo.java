package com.prize.lottery.application.query.vo;

import com.prize.lottery.domain.facade.dto.UserInfo;
import com.prize.lottery.infrast.persist.po.OrderInfoPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdmOrderInfoVo extends OrderInfoPo {

    private UserInfo user;

}
