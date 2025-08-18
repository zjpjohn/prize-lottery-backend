package com.prize.lottery.infrast.persist.vo;

import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.UserState;
import com.prize.lottery.infrast.persist.po.AgentApplyPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserAgentApplyVo extends AgentApplyPo {

    /**
     * 用户名称
     */
    private String     nickname;
    /**
     * 用户手机号
     */
    private String     phone;
    /**
     * 当前账户代理级别
     */
    private AgentLevel agent;
    /**
     * 邀请账户邀请码
     */
    private String    code;
    /**
     * 当前邀请账户状态
     */
    private UserState userState;

}
