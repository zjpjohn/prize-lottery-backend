package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.agent.model.AgentAcctDo;
import com.prize.lottery.domain.agent.repository.IAgentAcctRepository;
import com.prize.lottery.domain.agent.valobj.AgentIncome;
import com.prize.lottery.domain.agent.valobj.AgentOperation;
import com.prize.lottery.infrast.persist.mapper.UserInviteMapper;
import com.prize.lottery.infrast.persist.po.AgentIncomePo;
import com.prize.lottery.infrast.persist.po.UserInvitePo;
import com.prize.lottery.infrast.repository.converter.UserInviteConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AgentAcctRepository implements IAgentAcctRepository {

    private final UserInviteMapper    mapper;
    private final UserInviteConverter converter;

    @Override
    public void save(AgentAcctDo acct) {
        //账户收益日志
        AgentIncome agentIncome = acct.getAgentIncome();
        if (agentIncome != null) {
            AgentIncomePo income = converter.toPo(agentIncome);
            int           result = mapper.addAgentIncome(income);
            if (result == 0) {
                return;
            }
        }
        //账户变动操作
        AgentOperation operation  = acct.getOperation();
        UserInvitePo   userInvite = converter.toAcctPo(operation);
        mapper.editUserInvite(userInvite);
    }

    @Override
    public Optional<AgentAcctDo> ofId(Long userId) {
        return Optional.ofNullable(mapper.getUserInvite(userId)).map(converter::toAcctDo);
    }

}
