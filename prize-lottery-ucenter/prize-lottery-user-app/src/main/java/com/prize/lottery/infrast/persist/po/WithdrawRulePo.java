package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.WithdrawRuleState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WithdrawRulePo {

    private Long              id;
    //提现场景
    private String            scene;
    //提现门槛
    private Long              throttle;
    //提现最大金额
    private Long              maximum;
    //提现间隔天数
    private Integer           interval;
    //提现规则状态
    private WithdrawRuleState state;
    //提现备注描述
    private String            remark;
    //规则启用时间
    private LocalDateTime     startTime;
    //规则创建时间
    private LocalDateTime     gmtCreate;
    //规则更新时间
    private LocalDateTime     gmtModify;

}
