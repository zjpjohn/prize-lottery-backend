package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.utils.Assert;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class DltRankQuery extends PageQuery {
    private static final long serialVersionUID = 7702199205475878430L;

    @NotNull(message = "类型不允许为空")
    @Enumerable(enums = DltChannel.class, message = "类型错误")
    private String type;

    @Enumerable(ranges = {"0", "1"}, message = "参数错误")
    private Integer vip;

    public PageCondition from(String period) {
        return this.from()
                   .setParam("period", Assert.notNull(period, "期号不允许为空"))
                   .setParam("last", LotteryEnum.DLT.lastPeriod(period));
    }
}
