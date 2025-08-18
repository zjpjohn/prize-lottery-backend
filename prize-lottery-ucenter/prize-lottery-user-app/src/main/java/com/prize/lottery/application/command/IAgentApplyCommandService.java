package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.AgentApplyConfirmCmd;

public interface IAgentApplyCommandService {

    void agentApply(Long userId);

    void cancelAgentApply(Long id, Long userId);

    void confirmApply(AgentApplyConfirmCmd cmd);

}
