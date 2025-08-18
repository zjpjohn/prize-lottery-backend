package com.prize.lottery.domain.agent.repository;

import com.prize.lottery.domain.agent.model.AgentAcctDo;

import java.util.Optional;

public interface IAgentAcctRepository {

    void save(AgentAcctDo agentAcct);

    Optional<AgentAcctDo> ofId(Long userId);

}
