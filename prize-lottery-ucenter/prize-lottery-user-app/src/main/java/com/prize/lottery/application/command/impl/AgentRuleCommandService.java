package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.AgentRuleAssembler;
import com.prize.lottery.application.command.IAgentRuleCommandService;
import com.prize.lottery.application.command.dto.AgentRuleCreateCmd;
import com.prize.lottery.application.command.dto.AgentRuleEditCmd;
import com.prize.lottery.domain.agent.model.AgentRuleDo;
import com.prize.lottery.domain.agent.repository.IAgentRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgentRuleCommandService implements IAgentRuleCommandService {

    private final IAgentRuleRepository repository;
    private final AgentRuleAssembler   assembler;

    @Override
    @Transactional
    public void addAgentRule(AgentRuleCreateCmd cmd) {
        AgentRuleDo agentRule = new AgentRuleDo(cmd.validate(), assembler::toDo);
        AggregateFactory.create(agentRule).save(repository::save);
    }

    @Override
    @Transactional
    public void editAgentRule(AgentRuleEditCmd cmd) {
        repository.ofRule(cmd.getId()).peek(root -> root.modify(cmd, assembler::toDo)).save(repository::save);
    }

    @Override
    @Transactional
    public void clearRules() {
        repository.clearRules();
    }

}
