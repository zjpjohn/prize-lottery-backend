package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.TimeUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class PackInfoCreateCmd {

    @NotBlank(message = "套餐名称为空")
    @Length(max = 30, message = "套餐名称长度不超过30")
    private String   name;
    @Length(max = 100, message = "套餐描述长度不超过100")
    private String   remark;
    @NotNull(message = "套餐价格错误")
    @Positive(message = "套餐价格为正数")
    private Long     price;
    @NotNull(message = "折扣价格错误")
    @Positive(message = "折扣价格为正数")
    private Long     discount;
    @NotNull(message = "时间市场单位为空")
    private TimeUnit unit;
    @Enumerable(ranges = {"0", "1"}, message = "试用标识错误")
    private Integer  onTrial = 0;
}
