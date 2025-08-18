package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.AgentRuleState;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AgentRuleQuery extends PageQuery {

    private static final long serialVersionUID = -1942095763010706382L;

    private AgentLevel     agent;
    private AgentRuleState state;

}
