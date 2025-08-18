package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.utils.Assert;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class VoucherCreateCmd {

    @NotBlank(message = "代金券名称为空")
    @Length(max = 50, message = "代金券名称最大长度50")
    private String  name;
    @Length(max = 100, message = "描述最大长度100")
    private String  remark;
    @NotNull(message = "代金券金额为空")
    @Positive(message = "代金券金额为正数")
    @Max(value = 100L, message = "代金券金额不超过100")
    private Integer voucher;
    @NotNull(message = "一次性标识为空")
    @Enumerable(ranges = {"0", "1"}, message = "一次性标识错误")
    private Integer disposable;
    @Positive(message = "间隔天数大于0")
    private Integer interval;
    @NotNull(message = "过期标识为空")
    @PositiveOrZero(message = "过期标识不小于0")
    private Integer expire;

    public VoucherCreateCmd validate() {
        if (disposable == 0) {
            Assert.notNull(this.interval, ResponseHandler.VOUCHER_INTERVAL_ERROR);
            Assert.state(this.interval > 0, ResponseHandler.VOUCHER_INTERVAL_ERROR);
        }
        return this;
    }

}
