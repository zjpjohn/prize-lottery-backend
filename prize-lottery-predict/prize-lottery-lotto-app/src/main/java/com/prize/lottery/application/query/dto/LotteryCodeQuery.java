package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.enums.CodeType;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class LotteryCodeQuery extends PageQuery {

    private static final long serialVersionUID = -3533887854045070776L;

    @NotNull(message = "彩票类型为空")
    private LotteryEnum lottery;
    @NotNull(message = "万能码类型为空")
    private CodeType    type;

}
