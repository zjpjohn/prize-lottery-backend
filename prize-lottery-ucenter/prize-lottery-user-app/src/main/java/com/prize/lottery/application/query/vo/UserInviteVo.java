package com.prize.lottery.application.query.vo;

import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.UserState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInviteVo {

    private Long          userId;
    //用户邀请码
    private String        code;
    //分享代理等级
    private AgentLevel    agent;
    //流量合作伙伴开关:0-否,1-是
    private Integer       partner;
    //是否正在申请中:0-否,1-是
    private Integer       applying;
    //当前账户收益余额
    private Integer       income;
    //累计邀请奖励
    private Integer       rewards;
    //用户邀请链接
    private String        invUri;
    //累计邀请人数
    private Integer       invites;
    //邀请码状态
    private UserState     state;
    //创建时间
    private LocalDateTime gmtCreate;

}
