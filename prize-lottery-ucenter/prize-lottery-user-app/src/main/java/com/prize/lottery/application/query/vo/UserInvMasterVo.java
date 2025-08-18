package com.prize.lottery.application.query.vo;

import com.prize.lottery.infrast.persist.enums.AgentLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInvMasterVo {

    //邀请主标识
    private Long       userId;
    //邀请主名称
    private String     nickName;
    //邀请主邀请码
    private String     code;
    //邀请主手机号
    private String     phone;
    //邀请是用户的级别
    private AgentLevel invLevel;
    //当前邀请主级别
    private AgentLevel agentLevel;

    public UserInvMasterVo(Long userId, String code, AgentLevel invLevel, AgentLevel agentLevel) {
        this.userId     = userId;
        this.code       = code;
        this.invLevel   = invLevel;
        this.agentLevel = agentLevel;
    }
}
