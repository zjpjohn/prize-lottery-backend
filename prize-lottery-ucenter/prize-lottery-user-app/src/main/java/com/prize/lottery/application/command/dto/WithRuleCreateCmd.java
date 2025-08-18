package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.utils.Assert;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;


@Data
public class WithRuleCreateCmd {

    @NotBlank(message = "场景值为空")
    @Enumerable(enums = TransferScene.class, message = "提现场景错误")
    private String  scene;
    /**
     * 提现门槛
     */
    @NotNull(message = "提现门槛为空")
    @Range(min = 100, message = "提现门槛错误")
    private Integer throttle;
    /**
     * 提现最大金额
     */
    @NotNull(message = "提现金额最大值为空")
    @Range(min = 100, max = 10000, message = "金额最大值错误")
    private Integer maximum;
    /**
     * 提现间隔
     */
    @NotNull(message = "提现间隔为空")
    @Range(min = 1, max = 30, message = "提现间隔1~30天")
    private Integer interval;
    /**
     * 规则备注信息
     */
    @Length(max = 100, message = "规则描述最大长度为100")
    private String  remark;

    public WithRuleCreateCmd validate() {
        Assert.state(this.maximum > this.throttle, ResponseHandler.RULE_MAX_ERROR);
        return this;
    }

}
