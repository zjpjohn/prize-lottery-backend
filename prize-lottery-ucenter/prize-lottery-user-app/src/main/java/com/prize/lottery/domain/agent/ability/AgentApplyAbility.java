package com.prize.lottery.domain.agent.ability;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.AgentApplyConfirmCmd;
import com.prize.lottery.domain.agent.model.AgentApplyDo;
import com.prize.lottery.domain.agent.repository.IAgentApplyRepository;
import com.prize.lottery.domain.user.model.UserInvite;
import com.prize.lottery.domain.user.repository.IUserInviteRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.AgentApplyState;
import com.prize.lottery.infrast.persist.enums.UserState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgentApplyAbility {

    public static final String INVALID_INVITE_REMARK = "邀请账户异常";

    private final IAgentApplyRepository agentApplyRepository;
    private final IUserInviteRepository userInviteRepository;

    /**
     * 申请审核
     */
    public void confirmApply(AgentApplyConfirmCmd cmd) {
        AgentApplyDo agentApply = agentApplyRepository.of(cmd.getId());
        Assert.notNull(agentApply, ResponseHandler.AGENT_APPLY_NOT_NULL);

        //审核邀请账户状态判断
        Aggregate<Long, UserInvite> aggregate  = userInviteRepository.ofId(agentApply.getUserId());
        AgentApplyState             applyState = cmd.getState();
        String                      remark     = cmd.getRemark();
        UserInvite                  userInvite = null;
        if (aggregate == null) {
            applyState = AgentApplyState.CANCEL;
            remark     = INVALID_INVITE_REMARK;
        } else {
            userInvite = aggregate.getRoot();
            //邀请账户异常处理
            if (userInvite.getState() != UserState.NORMAL) {
                applyState = AgentApplyState.CANCEL;
                remark     = INVALID_INVITE_REMARK;
            }
        }
        agentApply.confirm(applyState, remark);
        //保存审核信息
        agentApplyRepository.save(agentApply);
        //审核通过，更新邀请账户代理类型
        if (userInvite != null && applyState == AgentApplyState.ADOPTED) {
            userInvite.applyAgent(cmd.getAgent());
            userInviteRepository.save(aggregate);
        }
    }

}
