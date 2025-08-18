package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AgentInvitedQuery;
import com.prize.lottery.application.query.dto.AgentUserQuery;
import com.prize.lottery.infrast.persist.vo.AgentInvitedUserVo;
import com.prize.lottery.infrast.persist.vo.AgentUserInviteVo;

import java.util.List;

public interface IUserInviteQueryService {

    List<AgentUserInviteVo> topUserInvite(Integer topN);

    Page<AgentUserInviteVo> getAgentUserList(AgentUserQuery query);

    AgentUserInviteVo getAgentUserDetail(Long userId);

    Page<AgentInvitedUserVo> getAgentInvitedList(AgentInvitedQuery query);

}
