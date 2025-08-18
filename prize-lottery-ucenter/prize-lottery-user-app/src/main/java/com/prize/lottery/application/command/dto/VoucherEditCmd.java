package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.VoucherState;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class VoucherEditCmd {

    @NotNull(message = "唯一标识为空")
    private Long         id;
    @Length(max = 50, message = "代金券名称最大长度50")
    private String       name;
    @Length(max = 100, message = "描述最大长度100")
    private String       remark;
    @Positive(message = "代金券金额为正数")
    @Max(value = 100L, message = "代金券金额不超过100")
    private Integer      voucher;
    @Enumerable(ranges = {"0", "1"}, message = "一次性标识错误")
    private Integer      disposable;
    @Positive(message = "间隔天数大于0")
    private Integer      interval;
    @PositiveOrZero(message = "过期标识不小于0")
    private Integer      expire;
    //代金券状态
    private VoucherState state;

}
