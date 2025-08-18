package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AgentIncomeQuery;
import com.prize.lottery.application.query.dto.AgentMetricsQuery;
import com.prize.lottery.application.query.vo.AgentAccountVo;
import com.prize.lottery.application.query.vo.AgentIncomeVo;
import com.prize.lottery.application.query.vo.AgentMetricsVo;

import java.util.List;

public interface IAgentAcctQueryService {

    AgentAccountVo agentAccount(Long userId);

    List<AgentMetricsVo> agentMetricsList(AgentMetricsQuery query);

    Page<AgentIncomeVo> agentIncomeList(AgentIncomeQuery query);
}
