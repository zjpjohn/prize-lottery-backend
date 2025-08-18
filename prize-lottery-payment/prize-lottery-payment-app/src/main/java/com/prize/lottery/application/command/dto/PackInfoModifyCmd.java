package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.PackState;
import com.prize.lottery.infrast.persist.enums.TimeUnit;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class PackInfoModifyCmd {

    @NotNull(message = "套餐编号为空")
    private String    packNo;
    @Length(max = 30, message = "套餐名称长度不超过30")
    private String    name;
    @Length(max = 100, message = "套餐描述长度不超过100")
    private String    remark;
    @Positive(message = "套餐价格为正数")
    private Long      price;
    @Positive(message = "折扣价格为正数")
    private Long      discount;
    @Enumerable(ranges = {"0", "1"}, message = "优先级标识错误")
    private Integer   priority;
    @Enumerable(ranges = {"0", "1"}, message = "试用标识错误")
    private Integer   onTrial;
    private PackState state;
    private TimeUnit  unit;

}
