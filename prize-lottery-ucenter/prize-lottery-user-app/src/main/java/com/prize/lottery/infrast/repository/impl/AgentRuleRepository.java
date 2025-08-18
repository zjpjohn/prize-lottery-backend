package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.agent.model.AgentRuleDo;
import com.prize.lottery.domain.agent.repository.IAgentRuleRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.AgentRuleState;
import com.prize.lottery.infrast.persist.mapper.UserInviteMapper;
import com.prize.lottery.infrast.persist.po.AgentRulePo;
import com.prize.lottery.infrast.repository.converter.UserInviteConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AgentRuleRepository implements IAgentRuleRepository {

    private final UserInviteMapper    mapper;
    private final UserInviteConverter converter;

    @Override
    public void save(Aggregate<Long, AgentRuleDo> aggregate) {
        AgentRuleDo root = aggregate.getRoot();
        if (root.isNew()) {
            AgentRulePo agentRule = converter.toPo(root);
            mapper.addAgentRule(agentRule);
            return;
        }
        aggregate.ifChanged().ifPresent(changed -> {
            AgentRulePo agentRule = converter.toPo(changed);
            agentRule.setAgent(root.getAgent());
            //当规则预启用或者使用时，先撤销已预发布或已使用的规则
            AgentRuleState state = agentRule.getState();
            if (state == AgentRuleState.PRE_START || state == AgentRuleState.USING) {
                mapper.autoRevokeAgentRule(agentRule);
            }
            mapper.editAgentRule(agentRule);
        });
    }

    @Override
    public Aggregate<Long, AgentRuleDo> ofRule(Long id) {
        return Optional.ofNullable(mapper.getAgentRuleById(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.AGENT_RULE_NOT_NULL);
    }

    @Override
    public Optional<AgentRuleDo> ofRuleAgent(AgentLevel agent, AgentRuleState state) {
        return Optional.ofNullable(mapper.getPreOrUsingAgentRule(agent, state)).map(converter::toDo);
    }

    @Override
    public void clearRules() {
        mapper.clearAgentRules();
    }

}
