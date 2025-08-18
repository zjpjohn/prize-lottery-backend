package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.enums.HitType;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class Num3WarnQuery extends PageQuery {

    private static final long serialVersionUID = 9194796948857826845L;

    @NotNull(message = "彩票类型错误")
    private LotteryEnum type;
    private HitType     hit;
    private String      period;

}
