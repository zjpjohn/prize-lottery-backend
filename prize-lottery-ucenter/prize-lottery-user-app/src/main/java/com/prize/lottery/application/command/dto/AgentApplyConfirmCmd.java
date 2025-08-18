package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.AgentApplyState;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


@Data
public class AgentApplyConfirmCmd {

    /**
     * 申请标识
     */
    @NotNull(message = "申请标识为空")
    private Long            id;
    /**
     * 审核状态
     */
    @NotNull(message = "审核状态为空")
    private AgentApplyState state;
    /**
     * 审核后赋权代理级别
     */
    private AgentLevel      agent;
    /**
     * 审核备注信息
     */
    private String          remark;

    public AgentApplyConfirmCmd validate() {
        switch (state) {
            case ADOPTED:
                Assert.notNull(agent, ResponseHandler.AGENT_LEVEL_NOT_NULL);
                break;
            case FAILED:
                Assert.state(StringUtils.isNotBlank(remark), ResponseHandler.CONFIRM_REMARK_NOT_NULL);
                break;
            default:
        }
        return this;
    }
}
