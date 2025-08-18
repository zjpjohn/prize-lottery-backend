package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppLaunchQuery extends PageQuery {
    private static final long serialVersionUID = 1359832192498342794L;

    @NotNull(message = "用户标识为空")
    private Long    userId;
    @Positive(message = "天数须为正数")
    @Enumerable(ranges = {"7", "14", "21", "28"}, message = "查询天数范围错误")
    private Integer day;

    @Override
    public PageCondition from() {
        PageCondition condition = super.from();
        if (this.day != null) {
            LocalDate endDay = LocalDate.now();
            condition.setParam("endDay", endDay);
            condition.setParam("startDay", endDay.minusDays(this.day));
        }
        return condition;
    }
}
