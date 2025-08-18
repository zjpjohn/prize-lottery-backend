package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.UserState;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AgentUserQuery extends PageQuery {

    private static final long serialVersionUID = 463222668404640423L;

    //用户代理等级
    private AgentLevel agent;
    //分享账户状态
    private UserState  state;
    //手机号
    private String     phone;
}
