package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.AgentApplyState;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AgentApplyQuery extends PageQuery {

    private static final long serialVersionUID = -1020662249401958872L;

    /**
     * 代理级别
     */
    private AgentLevel      agent;
    /**
     * 申请状态
     */
    private AgentApplyState state;
    /**
     * 用户手机号
     */
    private String          phone;

}
