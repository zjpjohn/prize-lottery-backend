package com.prize.lottery.application.query.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AgentAccountVo {

    /**
     * 账户标识
     */
    private Long          userId;
    /**
     * 提现场景标识
     */
    private TransferScene scene;
    /**
     * 账户级别
     */
    private AgentLevel    agent;
    /**
     * 累计提现奖励金数量
     */
    private Integer       withdraw;
    /**
     * 累计提现人民币数量
     */
    private Integer       withRmb;
    /**
     * 最新提现日期
     */
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate     withLatest;
    /**
     * 账户奖励金余额
     */
    private Integer       income;
    /**
     * 总邀请用户数
     */
    private Integer       invites;
    /**
     * 累计获得收益人次
     */
    private Integer       users;
    /**
     * 今日奖励金收益
     */
    private Integer       todayIncome  = 0;
    /**
     * 今日获得收益来源用户数量
     */
    private Integer       todayUsers   = 0;
    /**
     * 今日邀请用户数量
     */
    private Integer       todayInvites = 0;

}
