package com.prize.lottery.application.query.vo;

import com.prize.lottery.infrast.persist.po.AgentWithdrawPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AgentWithdrawVo extends AgentWithdrawPo {

    private String nickname;
    private String phone;

}
