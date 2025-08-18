package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.FeedsType;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class FeedAdmQuery extends PageQuery {

    private static final long serialVersionUID = 155808946643342360L;

    @Enumerable(enums = LotteryEnum.class, message = "彩种类型错误")
    private String    type;
    @Enumerable(ranges = {"0", "1"}, message = "状态值错误")
    private Integer   state;
    @Enumerable(ranges = {"1", "7", "14", "21"}, message = "查询天数错误")
    private Integer   day;
    private FeedsType feedType;

    @Override
    public PageCondition from() {
        PageCondition condition = super.from();
        if (day != null) {
            LocalDateTime time = LocalDateTime.now();
            condition.setParam("time", time);
            condition.setParam("startTime", time.minusDays(day));
        }
        return condition;
    }
}
