package com.prize.lottery.domain.user.ability;

import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.agent.model.AgentRuleDo;
import com.prize.lottery.domain.agent.repository.IAgentRuleRepository;
import com.prize.lottery.domain.user.event.InviteRewardEvent;
import com.prize.lottery.domain.user.model.UserInvite;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import com.prize.lottery.domain.user.repository.IUserInviteRepository;
import com.prize.lottery.infrast.persist.enums.AgentRuleState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InviteDomainService {

    private final IUserInviteRepository  userInviteRepository;
    private final IUserBalanceRepository userBalanceRepository;
    private final IAgentRuleRepository   agentRuleRepository;

    /**
     * 用户邀请获得金币奖励计算
     *
     * @param event 邀请奖励领域事件
     */
    @Async
    @EventListener
    public void inviteReward(InviteRewardEvent event) {
        //账户冻结或者无效不下发奖励邀请奖励
        Aggregate<Long, UserInvite> aggregate = userInviteRepository.ofCode(event.getInvCode())
                                                                    .filter(agg -> agg.getRoot().isNormal())
                                                                    .orElse(null);
        if (aggregate == null) {
            return;
        }
        UserInvite invite = aggregate.getRoot();
        Integer reward = agentRuleRepository.ofRuleAgent(invite.getAgent(), AgentRuleState.USING)
                                            .map(AgentRuleDo::getReward)
                                            .orElse(0);
        //用户邀请奖励计算
        invite.invite(event.getUserId(), reward);
        userInviteRepository.save(aggregate);
        //存在奖励金更新账户奖励金余额
        if (reward > 0) {
            userBalanceRepository.ofId(invite.getUserId()).ifPresent(balance -> {
                balance.inviteSurplus(reward);
                userBalanceRepository.save(balance);
            });
        }
    }

}
