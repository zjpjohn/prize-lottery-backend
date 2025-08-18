package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.agent.model.AgentApplyDo;
import com.prize.lottery.domain.agent.repository.IAgentApplyRepository;
import com.prize.lottery.infrast.persist.mapper.UserInviteMapper;
import com.prize.lottery.infrast.persist.po.AgentApplyPo;
import com.prize.lottery.infrast.repository.converter.AgentApplyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class AgentApplyRepository implements IAgentApplyRepository {

    private final UserInviteMapper    userInviteMapper;
    private final AgentApplyConverter agentApplyConverter;

    @Override
    public void save(AgentApplyDo apply) {
        AgentApplyPo applyPo = agentApplyConverter.toPo(apply);
        if (applyPo.getId() == null) {
            userInviteMapper.addAgentApply(applyPo);
            return;
        }
        userInviteMapper.editAgentApply(applyPo);
    }

    @Override
    public AgentApplyDo of(Long id) {
        AgentApplyPo agentApply = userInviteMapper.getAgentApply(id);
        if (agentApply != null) {
            return agentApplyConverter.toDo(agentApply);
        }
        return null;
    }

    @Override
    public AgentApplyDo ofApplying(Long userId) {
        AgentApplyPo agentApply = userInviteMapper.getApplyingAgentApply(userId);
        if (agentApply != null) {
            return agentApplyConverter.toDo(agentApply);
        }
        return null;
    }

}
