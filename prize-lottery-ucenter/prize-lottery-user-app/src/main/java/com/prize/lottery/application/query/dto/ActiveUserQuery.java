package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActiveUserQuery extends PageQuery {

    private static final long serialVersionUID = 5704078375982800870L;

    @Enumerable(ranges = {"0", "1", "7", "14", "21", "30"}, message = "查询天数错误")
    private Integer days;

    @Override
    public PageCondition from() {
        PageCondition condition = super.from();
        LocalDateTime end, start;
        if (days == 1) {
            //days=1表示昨天
            end   = LocalDateTime.now().minusDays(days).with(LocalTime.MAX);
            start = end.with(LocalTime.MIN);
        } else {
            end   = LocalDateTime.now();
            start = end.minusDays(days).with(LocalTime.MIN);
        }
        return condition.setParam("start", start).setParam("end", end);
    }

}
