package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.UserState;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserInvitePo {

    //代理人标识
    private Long       userId;
    //代理类型
    private AgentLevel agent;
    //用户邀请码
    private String     code;
    //用户邀请链接
    private String        invUri;
    //累计邀请人数
    private Integer       invites;
    //累计邀请获得奖励金
    private Integer       rewards;
    //流量主账户奖励金余额
    private Long          income;
    //累计收益用户人次
    private Integer       userAmt;
    //累计获得奖励金收益
    private Long          withdraw;
    //累计提现金额
    private Long          withRmb;
    //最近提现日期
    private LocalDate     withLatest;
    //数据版本
    private Integer       version;
    //状态
    private UserState     state;
    //创建时间
    private LocalDateTime gmtCreate;
    //更新时间
    private LocalDateTime gmtModify;

}
