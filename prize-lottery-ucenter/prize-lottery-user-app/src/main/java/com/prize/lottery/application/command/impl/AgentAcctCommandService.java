package com.prize.lottery.application.command.impl;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.prize.lottery.application.command.IAgentAcctCommandService;
import com.prize.lottery.application.command.executor.AgentMetricsCalcExecutor;
import com.prize.lottery.infrast.persist.mapper.UserInviteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentAcctCommandService implements IAgentAcctCommandService {

    private final UserInviteMapper         userInviteMapper;
    private final AgentMetricsCalcExecutor metricsCalcExecutor;

    @Override
    public void agentMetrics(LocalDate date) {
        List<Long> userIds = userInviteMapper.getAgentUserIdList();
        if (!CollectionUtils.isEmpty(userIds)) {
            List<List<Long>> partitions = Lists.partition(userIds, 50);
            for (List<Long> userList : partitions) {
                metricsCalcExecutor.execute(userList, date);
            }
        }
    }

}
