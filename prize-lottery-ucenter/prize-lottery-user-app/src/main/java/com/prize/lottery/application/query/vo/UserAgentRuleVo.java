package com.prize.lottery.application.query.vo;

import com.prize.lottery.infrast.persist.enums.AgentLevel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAgentRuleVo {

    //代理等级
    private AgentLevel    agent;
    //是否分润
    private Integer       profited;
    //分润比例
    private Double        ratio;
    //金币收益(普通用户适用)
    private Integer       reward;
    //生效时间
    private LocalDateTime startTime;

}
