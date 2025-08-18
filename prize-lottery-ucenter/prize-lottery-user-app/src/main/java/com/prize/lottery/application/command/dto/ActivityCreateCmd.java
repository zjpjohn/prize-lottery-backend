package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
public class ActivityCreateCmd {

    @NotBlank(message = "活动名称为空")
    private String       name;
    @NotNull(message = "抽取会员有效期为空")
    @Range(min = 1, max = 10, message = "有效期无效")
    private Integer      duration;
    @NotNull(message = "抽奖最小机会次数为空")
    @Range(min = 1, max = 10, message = "抽奖最小机会次数错误")
    private Integer      minimum;
    @NotNull(message = "失败次数后必中为空")
    @Range(min = 1, max = 50, message = "失败次数后错误")
    private Integer      throttle;
    @NotEmpty(message = "活动描述信息为空")
    private List<String> remark;

}
