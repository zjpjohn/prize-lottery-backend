package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class LotteryIndexQuery extends PageQuery {

    private static final long serialVersionUID = 5387162169203555419L;

    private String period;
    @NotBlank(message = "彩票类型为空")
    @Enumerable(enums = LotteryEnum.class, message = "彩票类型错误")
    private String lottery;

}
