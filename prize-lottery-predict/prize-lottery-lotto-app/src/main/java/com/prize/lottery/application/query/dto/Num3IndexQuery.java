package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class Num3IndexQuery extends PageQuery {

    private static final long serialVersionUID = -4852544582705798382L;

    @NotNull(message = "彩种类型错误")
    private LotteryEnum type;

}
