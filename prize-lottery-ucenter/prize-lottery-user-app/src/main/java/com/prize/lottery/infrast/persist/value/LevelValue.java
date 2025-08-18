package com.prize.lottery.infrast.persist.value;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class LevelValue {

    //提现等级值
    @NotNull(message = "提现等级值为空")
    @Min(value = 1, message = "提现等级值不小于1")
    private Integer value;
    //提现等级优先级
    @Min(value = 1, message = "提现等级值不小于1")
    private Integer priority;
    //提现等级描述
    @Length(max = 10, message = "描述信息不超过10个字")
    private String  remark;
    //提现等级标签
    @Length(max = 6, message = "标签信息不超过6个字")
    private String  tag;

}
