package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class LottoHoneyQuery extends PageQuery {

    private static final long serialVersionUID = -1631508699566256637L;

    @NotNull(message = "彩票类型为空")
    private LotteryEnum type;

}
