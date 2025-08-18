package com.prize.lottery.application.command.executor;

import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.infrast.persist.mapper.UserInviteMapper;
import com.prize.lottery.infrast.persist.po.AgentMetricsPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgentMetricsCalcExecutor {

    private final UserInviteMapper userInviteMapper;

    @Transactional
    public void execute(List<Long> userIds, LocalDate date) {
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        //流量主分组收益数据
        List<AgentMetricsPo>      incomeMetrics = userInviteMapper.getGroupedIncomeMetrics(userIds, date);
        Map<Long, AgentMetricsPo> incomeMap     = CollectionUtils.toMap(incomeMetrics, AgentMetricsPo::getUserId);
        //流量主分组邀请数据
        List<AgentMetricsPo>      inviteMetrics = userInviteMapper.getGroupedInviteMetrics(userIds, date);
        Map<Long, AgentMetricsPo> inviteMap     = CollectionUtils.toMap(inviteMetrics, AgentMetricsPo::getUserId);
        List<AgentMetricsPo> metricsList = userIds.stream().map(userId -> {
            AgentMetricsPo metrics = new AgentMetricsPo(userId, date);
            Optional.ofNullable(incomeMap.get(userId)).ifPresent(value -> {
                metrics.setUsers(value.getUsers());
                metrics.setAmount(value.getAmount());
            });
            Optional.ofNullable(inviteMap.get(userId)).ifPresent(value -> {
                metrics.setInvites(value.getInvites());
            });
            return metrics;
        }).collect(Collectors.toList());
        userInviteMapper.addAgentMetricsList(metricsList);
    }
}
