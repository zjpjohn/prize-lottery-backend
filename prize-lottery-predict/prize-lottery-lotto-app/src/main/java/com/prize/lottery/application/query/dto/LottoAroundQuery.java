package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class LottoAroundQuery extends PageQuery {

    private static final long serialVersionUID = -3272546946470992837L;

    @NotNull(message = "彩票类型为空")
    private LotteryEnum lotto;

}
