package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.agent.model.AgentAcctDo;
import com.prize.lottery.domain.agent.model.AgentRuleDo;
import com.prize.lottery.domain.agent.valobj.AgentIncome;
import com.prize.lottery.domain.agent.valobj.AgentOperation;
import com.prize.lottery.domain.user.model.UserInvite;
import com.prize.lottery.domain.user.valobj.InviteRewardVal;
import com.prize.lottery.domain.user.valobj.UserInviteLog;
import com.prize.lottery.infrast.persist.po.AgentIncomePo;
import com.prize.lottery.infrast.persist.po.AgentRulePo;
import com.prize.lottery.infrast.persist.po.UserInviteLogPo;
import com.prize.lottery.infrast.persist.po.UserInvitePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserInviteConverter {

    UserInvitePo toPo(UserInvite invite);

    @Mapping(source = "rewardVal.reward", target = "rewards")
    @Mapping(source = "rewardVal.invite", target = "invites")
    UserInvitePo toPo(Long userId, InviteRewardVal rewardVal);

    UserInvite toDo(UserInvitePo invite);

    UserInviteLogPo toPo(UserInviteLog log);

    UserInviteLog toValue(UserInviteLogPo log);

    AgentIncomePo toPo(AgentIncome income);

    AgentRuleDo toDo(AgentRulePo rule);

    AgentRulePo toPo(AgentRuleDo rule);

    AgentAcctDo toAcctDo(UserInvitePo invite);

    UserInvitePo toAcctPo(AgentOperation operation);

}
