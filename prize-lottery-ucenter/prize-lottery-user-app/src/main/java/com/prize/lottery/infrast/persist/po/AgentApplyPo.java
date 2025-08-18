package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.AgentApplyState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgentApplyPo {

    private Long            id;
    /**
     * 申请代理人标识
     */
    private Long            userId;
    /**
     * 申请备注信息
     */
    private String          remark;
    /**
     * 申请状态
     */
    private AgentApplyState state;
    /**
     * 创建时间
     */
    private LocalDateTime   gmtCreate;
    /**
     * 更新时间
     */
    private LocalDateTime   gmtModify;

}
