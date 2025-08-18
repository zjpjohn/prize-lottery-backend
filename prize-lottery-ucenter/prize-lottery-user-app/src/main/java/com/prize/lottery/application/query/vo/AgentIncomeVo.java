package com.prize.lottery.application.query.vo;

import com.prize.lottery.infrast.persist.po.AgentIncomePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AgentIncomeVo extends AgentIncomePo {

    private String nickname;
    private String phone;

}
