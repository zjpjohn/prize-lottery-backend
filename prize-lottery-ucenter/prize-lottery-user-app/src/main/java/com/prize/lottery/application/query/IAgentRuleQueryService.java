package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AgentRuleQuery;
import com.prize.lottery.application.query.vo.UserAgentRuleVo;
import com.prize.lottery.infrast.persist.po.AgentRulePo;

import java.util.List;

public interface IAgentRuleQueryService {

    UserAgentRuleVo getUserAgentRule(Long userId);

    Page<AgentRulePo> getAgentRuleList(AgentRuleQuery query);

    List<AgentRulePo> getAllUsingAgentRules();

}
