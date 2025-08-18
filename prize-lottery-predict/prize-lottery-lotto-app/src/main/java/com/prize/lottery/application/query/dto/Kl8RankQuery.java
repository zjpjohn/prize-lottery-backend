package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.utils.Assert;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.utils.PeriodCalculator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class Kl8RankQuery extends PageQuery {

    private static final long serialVersionUID = 8493896090954621781L;

    /**
     * 查询指定期号
     */
    private String period;
    @NotNull(message = "数据类型不允许为空.")
    @Enumerable(enums = Kl8Channel.class, message = "查询类型错误.")
    private String type;


    @Override
    public PageCondition from() {
        Assert.notNull(period, "期号不允许为空.");
        return super.from().setParam("last", PeriodCalculator.fc3dPeriod(period, 1));
    }

}
