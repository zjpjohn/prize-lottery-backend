package com.prize.lottery.domain.agent.model;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.AgentApplyState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgentApplyDo {

    private Long            id;
    private Long            userId;
    private AgentApplyState state;
    private String          remark;

    public AgentApplyDo(Long userId) {
        this.userId = userId;
        this.state  = AgentApplyState.APPLYING;
    }

    /**
     * 用户取消流量主申请
     *
     * @param userId 用户标识
     */
    public void cancel(Long userId) {
        Assert.state(this.userId.equals(userId), ResponseHandler.AGENT_APPLY_FORBIDDEN);
        Assert.state(this.state == AgentApplyState.APPLYING, ResponseHandler.AGENT_APPLY_STATE_ERROR);
        this.state = AgentApplyState.CANCEL;
    }

    /**
     * 确认审核申请
     *
     * @param state  审核状态
     * @param remark 备注信息
     */
    public void confirm(AgentApplyState state, String remark) {
        Assert.state(this.state == AgentApplyState.APPLYING, ResponseHandler.AGENT_APPLY_STATE_ERROR);
        this.state  = state;
        this.remark = remark;
    }

}
