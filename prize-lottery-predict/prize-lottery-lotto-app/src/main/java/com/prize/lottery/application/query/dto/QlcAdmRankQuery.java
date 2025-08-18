package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.utils.Assert;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.QlcChannel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class QlcAdmRankQuery extends PageQuery {

    private static final long serialVersionUID = -2304292469848238234L;

    /**
     * 预测字段类型
     */
    @NotNull(message = "类型不允许为空")
    @Enumerable(enums = QlcChannel.class, message = "字段类型错误")
    private String type;
    /**
     * 预测期号
     */
    private String period;

    @Override
    public PageCondition from() {
        Assert.notNull(this.period, "期号不允许为空");
        return super.from().setParam("last", LotteryEnum.QLC.lastPeriod(this.period));
    }

}
