package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.AgentRuleState;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
public class AgentRuleEditCmd {

    @NotNull(message = "规则标识为空")
    private Long           id;
    /**
     * 分润标识
     */
    @Enumerable(ranges = {"0", "1"}, message = "可选值['0','1']")
    private Integer        profited;
    /**
     * 分润比例0~1
     */
    @DecimalMin(value = "0", message = "不允许小于0")
    @DecimalMax(value = "1", message = "不允许大于1")
    private Double         ratio;
    /**
     * 金币奖励>1
     */
    @Range(min = 0, message = "不允许小于0")
    private Integer        reward;
    /**
     * 代理商规则状态
     */
    private AgentRuleState state;
    /**
     * 规则启用时间：规则预发布指定规则启用时间
     */
    @Future(message = "请输入未来时间")
    private LocalDateTime  startTime;

}
