package com.prize.lottery.domain.agent.repository;


import com.prize.lottery.domain.agent.model.AgentApplyDo;

import java.util.Optional;

public interface IAgentApplyRepository {

    void save(AgentApplyDo apply);

    AgentApplyDo of(Long id);

    AgentApplyDo ofApplying(Long userId);


}
