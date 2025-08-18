package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.WithdrawRuleState;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
public class WithRuleEditCmd {

    /**
     * 规则标识
     */
    @NotNull(message = "规则标识为空")
    private Long              id;
    /**
     * 提现门槛
     */
    @Range(min = 1, message = "提现门槛错误")
    private Long              throttle;
    /**
     * 金额最大值
     */
    @Range(min = 1, message = "金额最大值错误")
    private Long              maximum;
    /**
     * 提现间隔天数
     */
    @Range(min = 1, max = 30, message = "提现间隔1~30天")
    private Integer           interval;
    /**
     * 规则备注信息
     */
    @Length(max = 100, message = "规则描述最大长度为100")
    private String            remark;
    /**
     * 提现规则状态
     */
    private WithdrawRuleState state;
    /**
     * 提现规则启用时，启用时间
     */
    @Future(message = "规则启用时间为未来时间")
    private LocalDateTime     startTime;

}
