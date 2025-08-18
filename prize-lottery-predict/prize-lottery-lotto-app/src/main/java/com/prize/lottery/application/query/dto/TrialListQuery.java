package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class TrialListQuery extends PageQuery {

    private static final long serialVersionUID = -7937433764929570869L;

    @NotNull(message = "开奖类型为空")
    private LotteryEnum type;

}
