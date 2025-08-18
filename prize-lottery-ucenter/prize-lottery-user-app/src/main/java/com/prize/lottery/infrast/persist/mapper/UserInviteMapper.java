package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.AgentRuleState;
import com.prize.lottery.infrast.persist.po.*;
import com.prize.lottery.infrast.persist.vo.AgentInvitedUserVo;
import com.prize.lottery.infrast.persist.vo.AgentUserInviteVo;
import com.prize.lottery.infrast.persist.vo.UserAgentApplyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface UserInviteMapper {

    int addUserInvite(UserInvitePo invite);

    int editUserInvite(UserInvitePo invite);

    UserInvitePo getUserInvite(Long userId);

    UserInvitePo getUserInviteByCode(String code);

    List<Long> getAgentUserIdList();

    int addUserInviteLog(UserInviteLogPo inviteLog);

    List<UserInviteLogPo> getInviteLogsByInvId(Long invUid);

    UserInviteLogPo getInviteLogByUserId(Long userId);

    int addAgentApply(AgentApplyPo apply);

    int editAgentApply(AgentApplyPo apply);

    AgentApplyPo getAgentApply(Long id);

    int hasApplyingAgentApply(Long userId);

    AgentApplyPo getApplyingAgentApply(Long userId);

    List<AgentApplyPo> getUserAgentApplies(Long userId);

    List<UserAgentApplyVo> getAgentApplyList(PageCondition condition);

    int countAgentApplies(PageCondition condition);

    int addAgentRule(AgentRulePo rule);

    int editAgentRule(AgentRulePo rule);

    int autoRevokeAgentRule(AgentRulePo rule);

    int dropdownAgentRule(@Param("agent") AgentLevel agent, @Param("state") AgentRuleState state);

    int deleteAgentRule(Long id);

    int clearAgentRules();

    AgentRulePo getAgentRuleById(Long id);

    AgentRulePo getPreOrUsingAgentRule(@Param("agent") AgentLevel agent, @Param("state") AgentRuleState state);

    List<AgentRulePo> getAllUsingAgentRules();

    List<AgentRulePo> getAgentRuleList(PageCondition condition);

    int countAgentRules(PageCondition condition);

    AgentUserInviteVo getAgentUserInfo(Long userId);

    List<AgentUserInviteVo> getAgentUserList(PageCondition condition);

    int countAgentUsers(PageCondition condition);

    List<AgentUserInviteVo> getAgentInvitesTopN(Integer limit);

    List<AgentInvitedUserVo> getAgentInvitedUsers(PageCondition condition);

    int countAgentInvitedUsers(PageCondition condition);

    int addAgentIncome(AgentIncomePo income);

    List<AgentIncomePo> getAgentIncomeList(PageCondition condition);

    int countAgentIncomes(PageCondition condition);

    int addAgentMetrics(AgentMetricsPo metrics);

    int addAgentMetricsList(List<AgentMetricsPo> metricsList);

    AgentMetricsPo getLatestMetrics(Long userId);

    Optional<AgentMetricsPo> getTodayInviteMetrics(Long userId);

    Optional<AgentMetricsPo> getTodayIncomeMetrics(Long userId);

    List<AgentMetricsPo> getGroupedIncomeMetrics(@Param("userIds") List<Long> userIds, @Param("date") LocalDate date);

    List<AgentMetricsPo> getGroupedInviteMetrics(@Param("userIds") List<Long> userIds, @Param("date") LocalDate date);

    List<AgentMetricsPo> getAgentMetricsList(@Param("userId") Long userId,
                                             @Param("startDay") LocalDate startDay,
                                             @Param("endDay") LocalDate endDay);

    int addAgentWithdraw(AgentWithdrawPo withdraw);

    int editAgentWithdraw(AgentWithdrawPo withdraw);

    AgentWithdrawPo getWithdrawById(Long id);

    AgentWithdrawPo getWithdrawBySeqNo(String seqNo);

    AgentWithdrawPo latestWithdraw(Long userId);

    List<AgentWithdrawPo> getAgentWithdrawList(PageCondition condition);

    int countAgentWithdraws(PageCondition condition);

}
