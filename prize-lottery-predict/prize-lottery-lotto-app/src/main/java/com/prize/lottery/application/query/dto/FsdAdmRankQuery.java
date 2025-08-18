package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.utils.Assert;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class FsdAdmRankQuery extends PageQuery {

    private static final long serialVersionUID = 369921582356777453L;

    /**
     * 预测期号
     */
    private String  period;
    /**
     * 字段类型
     */
    @NotNull(message = "类型不允许为空")
    @Enumerable(enums = Fc3dChannel.class, message = "类型错误")
    private String  type;
    /**
     * 是否仅允许查询标记预测数据
     */
    @Enumerable(ranges = {"0", "1"}, message = "参数值仅允许0,1")
    private Integer mark;


    @Override
    public PageCondition from() {
        Assert.notNull(this.period, "期号不允许为空");
        return super.from().setParam("last", LotteryEnum.FC3D.lastPeriod(period));
    }

}
