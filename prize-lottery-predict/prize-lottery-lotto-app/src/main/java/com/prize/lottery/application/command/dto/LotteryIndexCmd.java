package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class LotteryIndexCmd {

    @NotNull(message = "用户标识为空")
    private Long   userId;
    @NotBlank(message = "彩种类型为空")
    @Enumerable(enums = LotteryEnum.class, message = "彩票种类错误")
    private String lottery;
    //指定查询期号
    private String period;

}
