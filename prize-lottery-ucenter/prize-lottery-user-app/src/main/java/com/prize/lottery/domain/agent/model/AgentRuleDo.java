package com.prize.lottery.domain.agent.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.AgentRuleCreateCmd;
import com.prize.lottery.application.command.dto.AgentRuleEditCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.AgentRuleState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class AgentRuleDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -5563068326453678912L;

    private Long           id;
    private AgentLevel     agent;
    private Integer        profited;
    private Double         ratio;
    private Integer        reward;
    private AgentRuleState state;
    private LocalDateTime  startTime;

    public AgentRuleDo(AgentRuleCreateCmd command, BiConsumer<AgentRuleCreateCmd, AgentRuleDo> converter) {
        this.state = AgentRuleState.CREATED;
        converter.accept(command, this);
    }

    public void modify(AgentRuleEditCmd command, BiConsumer<AgentRuleEditCmd, AgentRuleDo> converter) {
        AgentRuleState newState = command.getState();
        if (newState == null) {
            Assert.state(this.state == AgentRuleState.CREATED, ResponseHandler.AGENT_RULE_STATE_ERROR);
            converter.accept(command, this);
            if (this.profited == 0) {
                Assert.state(this.reward > 0, ResponseHandler.REWARD_VALUE_ERROR);
            }
            return;
        }
        Set<AgentRuleState> transitions = this.state.transitions();
        Assert.state(transitions.contains(state), ResponseHandler.DATA_STATE_ILLEGAL);
        this.state = newState;
        if (state == AgentRuleState.USING) {
            this.startTime = LocalDateTime.now();
            AgentRuleHint.of(this).pushUseMessage();
        } else if (state == AgentRuleState.PRE_START) {
            LocalDateTime startTime = command.getStartTime();
            Assert.notNull(startTime, ResponseHandler.RULE_PRE_START_TIME_NONE);
            this.startTime = startTime;
            AgentRuleHint.of(this).pushPreMessage();
        }
    }

    public boolean isShareBenefit() {
        return this.profited != null && this.profited == 1;
    }

    public Integer calcIncome(Integer bounty) {
        return Double.valueOf(bounty * this.ratio).intValue();
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
