package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.AgentRuleCreateCmd;
import com.prize.lottery.application.command.dto.AgentRuleEditCmd;

public interface IAgentRuleCommandService {

    void addAgentRule(AgentRuleCreateCmd cmd);

    void editAgentRule(AgentRuleEditCmd cmd);

    void clearRules();

}
