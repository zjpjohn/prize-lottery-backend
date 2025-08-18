package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class LotteryListQuery extends PageQuery {

    private static final long serialVersionUID = 5136809981634565622L;

    @NotBlank(message = "彩票类型为空")
    @Enumerable(enums = LotteryEnum.class, message = "彩票类型错误.")
    private String type;

    @Enumerable(ranges = {"0", "1"}, message = "参数错误,仅允许0或1")
    private Integer noneNull = 0;

    @Enumerable(ranges = {"1", "2", "3", "4", "5", "6", "7"}, message = "星期错误")
    private Integer week;
}
