package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.utils.Assert;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;


@Data
public class AgentRuleCreateCmd {

    /**
     * 代理类型
     */
    @NotNull(message = "代理类型为空")
    private AgentLevel agent;
    /**
     * 分润标识
     */
    @NotNull(message = "是否分润为空")
    @Enumerable(ranges = {"0", "1"}, message = "可选值['0','1']")
    private Integer    profited;
    /**
     * 分润比例0~1
     */
    @DecimalMin(value = "0", message = "不允许小于0")
    @DecimalMax(value = "1", message = "不允许大于1")
    private Double     ratio;
    /**
     * 金币奖励>0
     */
    @Range(min = 0, message = "不允许小于0")
    private Integer    reward;

    /**
     * 规则校验
     */
    public AgentRuleCreateCmd validate() {
        if (agent == AgentLevel.NORMAL) {
            Assert.notNull(reward, ResponseHandler.REWARD_RULE_NOT_NULL);
            Assert.state(reward > 0, ResponseHandler.REWARD_VALUE_ERROR);
        } else {
            Assert.notNull(ratio, ResponseHandler.RATIO_RULE_NOT_NULL);
        }
        return this;
    }
}
