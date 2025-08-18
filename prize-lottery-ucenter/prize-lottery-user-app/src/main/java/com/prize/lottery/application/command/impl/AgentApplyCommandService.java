package com.prize.lottery.application.command.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.IAgentApplyCommandService;
import com.prize.lottery.application.command.dto.AgentApplyConfirmCmd;
import com.prize.lottery.domain.agent.ability.AgentApplyAbility;
import com.prize.lottery.domain.agent.model.AgentApplyDo;
import com.prize.lottery.domain.agent.repository.IAgentApplyRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgentApplyCommandService implements IAgentApplyCommandService {

    private final IAgentApplyRepository agentApplyRepository;
    private final AgentApplyAbility     agentApplyAbility;

    @Override
    public void agentApply(Long userId) {
        AgentApplyDo agentApply = agentApplyRepository.ofApplying(userId);
        Assert.state(agentApply == null, ResponseHandler.AGENT_APPLY_HAS_EXIST);

        AgentApplyDo applyDo = new AgentApplyDo(userId);
        agentApplyRepository.save(applyDo);
    }

    @Override
    public void cancelAgentApply(Long id, Long userId) {
        AgentApplyDo agentApply = agentApplyRepository.of(id);
        Assert.notNull(agentApply, ResponseHandler.AGENT_APPLY_NOT_NULL);

        //取消代理申请
        agentApply.cancel(userId);
        agentApplyRepository.save(agentApply);
    }

    @Override
    @Transactional
    public void confirmApply(AgentApplyConfirmCmd command) {
        agentApplyAbility.confirmApply(command.validate());
    }

}
