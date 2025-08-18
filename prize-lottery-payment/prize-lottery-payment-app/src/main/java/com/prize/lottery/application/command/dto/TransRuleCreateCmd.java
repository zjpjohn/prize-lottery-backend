package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.utils.Assert;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class TransRuleCreateCmd {

    @NotBlank(message = "场景值为空")
    private String  scene;
    @Positive(message = "提现审核门槛为正数")
    private Long    throttle;
    @NotNull(message = "强制审核标识为空")
    @Enumerable(ranges = {"0", "1"}, message = "强制审核标识错误")
    private Integer force;
    @Length(max = 100, message = "规则描述最大长度为100")
    private String  remark;

    public TransRuleCreateCmd validate() {
        if (this.force == 0) {
            Assert.notNull(this.throttle, ResponseHandler.AUDIT_THROTTLE_NONE_NULL);
            return this;
        }
        this.force = null;
        return this;
    }
}
