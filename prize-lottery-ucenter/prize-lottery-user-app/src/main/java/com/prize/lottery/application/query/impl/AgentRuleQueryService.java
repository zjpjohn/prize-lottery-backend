package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.assembler.UserInfoAssembler;
import com.prize.lottery.application.query.IAgentRuleQueryService;
import com.prize.lottery.application.query.dto.AgentRuleQuery;
import com.prize.lottery.application.query.vo.UserAgentRuleVo;
import com.prize.lottery.infrast.persist.enums.AgentRuleState;
import com.prize.lottery.infrast.persist.mapper.UserInviteMapper;
import com.prize.lottery.infrast.persist.po.AgentRulePo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgentRuleQueryService implements IAgentRuleQueryService {

    private final UserInviteMapper  userInviteMapper;
    private final UserInfoAssembler userInfoAssembler;

    @Override
    public UserAgentRuleVo getUserAgentRule(Long userId) {
        return Optional.ofNullable(userInviteMapper.getUserInvite(userId))
                       .map(e -> userInviteMapper.getPreOrUsingAgentRule(e.getAgent(), AgentRuleState.USING))
                       .map(userInfoAssembler::toVo)
                       .orElse(null);
    }

    @Override
    public Page<AgentRulePo> getAgentRuleList(AgentRuleQuery query) {
        return query.from().count(userInviteMapper::countAgentRules).query(userInviteMapper::getAgentRuleList);
    }

    @Override
    public List<AgentRulePo> getAllUsingAgentRules() {
        return userInviteMapper.getAllUsingAgentRules();
    }

}
