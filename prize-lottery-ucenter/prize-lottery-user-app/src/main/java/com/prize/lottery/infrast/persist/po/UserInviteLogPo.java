package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.AgentLevel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInviteLogPo {

    /**
     * 被邀请用户标识
     */
    private Long          userId;
    /**
     * 邀请主用户标识
     */
    private Long          invUid;
    /**
     * 邀请主当前流量级别
     */
    private AgentLevel    invAgent;
    /**
     * 当前获得奖励
     */
    private Integer       invReward;
    /**
     * 邀请创建时间
     */
    private LocalDateTime gmtCreate;

}
