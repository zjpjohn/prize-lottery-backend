package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class PutRecordCreateCmd {

    @NotNull(message = "渠道标识为空")
    private String  channel;
    @NotNull(message = "预期金额为空")
    @PositiveOrZero(message = "预期金额不小于0")
    private Long    expectAmt;
    @Enumerable(ranges = {"1", "2"}, message = "投放状态错误")
    private Integer state;
    @NotBlank(message = "投放说明为空")
    @Length(max = 100, message = "说明最多100个字")
    private String  remark;

}
