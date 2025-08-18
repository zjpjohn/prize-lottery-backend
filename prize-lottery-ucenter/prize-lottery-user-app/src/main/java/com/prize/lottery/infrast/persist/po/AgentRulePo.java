package com.prize.lottery.infrast.persist.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.AgentRuleState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgentRulePo {

    private Long       id;
    /**
     * 代理级别
     */
    private AgentLevel agent;
    /**
     * 是否分润:0-否,1-是
     */
    private Integer    profited;
    /**
     * 分润比例
     */
    private Double         ratio;
    /**
     * 金币收益
     */
    private Integer        reward;
    /**
     * 规则状态
     */
    private AgentRuleState state;
    /**
     * 启用时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime  startTime;
    /**
     * 创建时间
     */
    private LocalDateTime  gmtCreate;
    /**
     * 更新时间
     */
    private LocalDateTime  gmtModify;
}
