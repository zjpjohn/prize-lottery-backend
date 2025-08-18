package com.prize.lottery.application.query.dto;

import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class LotteryPickQuery {

    @NotNull(message = "彩种类型为空")
    private LotteryEnum lottery;
    @NotNull(message = "用户标识为空")
    private Long        userId;
    private String      period;

}
