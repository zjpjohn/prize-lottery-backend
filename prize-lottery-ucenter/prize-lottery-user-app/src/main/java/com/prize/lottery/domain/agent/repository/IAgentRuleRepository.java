package com.prize.lottery.domain.agent.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.agent.model.AgentRuleDo;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.AgentRuleState;

import java.util.Optional;

public interface IAgentRuleRepository {

    void save(Aggregate<Long, AgentRuleDo> aggregate);

    Aggregate<Long, AgentRuleDo> ofRule(Long id);

    Optional<AgentRuleDo> ofRuleAgent(AgentLevel agent, AgentRuleState state);

    void clearRules();

}
