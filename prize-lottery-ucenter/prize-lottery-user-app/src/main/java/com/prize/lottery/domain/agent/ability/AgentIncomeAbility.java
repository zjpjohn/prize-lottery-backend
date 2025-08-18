package com.prize.lottery.domain.agent.ability;

import com.prize.lottery.domain.agent.model.AgentAcctDo;
import com.prize.lottery.domain.agent.model.AgentRuleDo;
import com.prize.lottery.domain.agent.repository.IAgentAcctRepository;
import com.prize.lottery.domain.agent.repository.IAgentRuleRepository;
import com.prize.lottery.domain.user.repository.IUserInviteRepository;
import com.prize.lottery.domain.user.valobj.UserInviteLog;
import com.prize.lottery.infrast.persist.enums.AgentRuleState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgentIncomeAbility {

    private final IAgentRuleRepository  agentRuleRepository;
    private final IUserInviteRepository userInviteRepository;
    private final IAgentAcctRepository  agentAcctRepository;

    @Async
    @Transactional
    public void execute(String seqNo, Long userId, Integer amount, Integer channel) {
        UserInviteLog inviteLog = userInviteRepository.ofUserLog(userId).orElse(null);
        if (inviteLog == null) {
            return;
        }
        agentRuleRepository.ofRuleAgent(inviteLog.getInvAgent(), AgentRuleState.USING)
                           .filter(AgentRuleDo::isShareBenefit)
                           .ifPresent(rule -> {
                               Integer income = rule.calcIncome(amount);
                               if (income > 0) {
                                   AgentAcctDo agentAcct = new AgentAcctDo(inviteLog.getInvUid());
                                   agentAcct.income(seqNo, userId, income.longValue(), rule.getRatio(), channel);
                                   agentAcctRepository.save(agentAcct);
                               }
                           });
    }

}
