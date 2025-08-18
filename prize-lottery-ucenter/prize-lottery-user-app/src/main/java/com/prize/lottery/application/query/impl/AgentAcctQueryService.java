package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.UserInfoAssembler;
import com.prize.lottery.application.query.IAgentAcctQueryService;
import com.prize.lottery.application.query.dto.AgentIncomeQuery;
import com.prize.lottery.application.query.dto.AgentMetricsQuery;
import com.prize.lottery.application.query.vo.AgentAccountVo;
import com.prize.lottery.application.query.vo.AgentIncomeVo;
import com.prize.lottery.application.query.vo.AgentMetricsVo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.mapper.UserInfoMapper;
import com.prize.lottery.infrast.persist.mapper.UserInviteMapper;
import com.prize.lottery.infrast.persist.po.AgentIncomePo;
import com.prize.lottery.infrast.persist.po.AgentMetricsPo;
import com.prize.lottery.infrast.persist.po.UserInfoPo;
import com.prize.lottery.infrast.persist.po.UserInvitePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentAcctQueryService implements IAgentAcctQueryService {

    private final UserInfoMapper    userInfoMapper;
    private final UserInviteMapper  userInviteMapper;
    private final UserInfoAssembler userInfoAssembler;

    @Override
    public AgentAccountVo agentAccount(Long userId) {
        //流量主账户信息
        UserInvitePo userInvite = userInviteMapper.getUserInvite(userId);
        Assert.notNull(userInvite, ResponseHandler.USER_BALANCE_NONE);
        AgentAccountVo agentAccount = userInfoAssembler.toVo(userInvite);
        agentAccount.setScene(TransferScene.USER_AGENT_TRANS);
        //今日流量主邀请用户数
        userInviteMapper.getTodayInviteMetrics(userId)
                        .map(AgentMetricsPo::getInvites)
                        .ifPresent(agentAccount::setTodayInvites);
        //今日账户收益数据
        userInviteMapper.getTodayIncomeMetrics(userId).ifPresent(metrics -> {
            agentAccount.setTodayIncome(metrics.getAmount());
            agentAccount.setTodayUsers(metrics.getUsers());
        });
        return agentAccount;
    }

    @Override
    public List<AgentMetricsVo> agentMetricsList(AgentMetricsQuery query) {
        Pair<LocalDate, LocalDate>     datePair    = query.getStartAndEndDay();
        LocalDate                      startDay    = datePair.getKey();
        List<AgentMetricsPo>           metricsList = userInviteMapper.getAgentMetricsList(query.getUserId(), startDay, datePair.getValue());
        Map<LocalDate, AgentMetricsPo> metricsMap  = CollectionUtils.toMap(metricsList, AgentMetricsPo::getDay);
        return IntStream.rangeClosed(0, query.getDays()).mapToObj(day -> {
            LocalDate date = startDay.plusDays(day);
            return Optional.ofNullable(metricsMap.get(date))
                           .map(userInfoAssembler::toVo)
                           .orElseGet(() -> new AgentMetricsVo(date));
        }).collect(Collectors.toList());
    }

    @Override
    public Page<AgentIncomeVo> agentIncomeList(AgentIncomeQuery query) {
        return query.from()
                    .count(userInviteMapper::countAgentIncomes)
                    .query(userInviteMapper::getAgentIncomeList)
                    .flatMap(list -> {
                        List<Long>            userIds  = CollectionUtils.distinctList(list, AgentIncomePo::getUserId);
                        List<UserInfoPo>      userList = userInfoMapper.getUserInfoByIdList(userIds);
                        Map<Long, UserInfoPo> userMap  = CollectionUtils.toMap(userList, UserInfoPo::getId);
                        return list.stream().map(v -> {
                            AgentIncomeVo income = userInfoAssembler.toVo(v);
                            UserInfoPo    user   = userMap.get(v.getUserId());
                            income.setPhone(user.getPhone());
                            income.setNickname(user.getNickname());
                            return income;
                        }).collect(Collectors.toList());
                    });
    }
}
