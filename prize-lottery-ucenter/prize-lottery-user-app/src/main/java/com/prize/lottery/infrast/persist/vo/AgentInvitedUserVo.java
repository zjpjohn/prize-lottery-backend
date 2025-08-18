package com.prize.lottery.infrast.persist.vo;

import com.prize.lottery.infrast.persist.enums.AgentLevel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgentInvitedUserVo {

    //被邀请用户标识
    private Long          userId;
    //被邀请用户名称
    private String        name;
    //被邀请用户手机号
    private String     phone;
    //流量主代理级别
    private AgentLevel agent;
    //流量主邀请用户获得奖励
    private Integer    reward;
    //邀请时间
    private LocalDateTime inviteTime;
    //最新启动应用时间
    private LocalDateTime latestLaunch;
    //启动应用总次数
    private Integer       launches;

}
