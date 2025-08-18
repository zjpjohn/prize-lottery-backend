package com.prize.lottery.application.query.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;

@Data
public class AgentMetricsQuery {

    @NotNull(message = "用户标识为空")
    private Long    userId;
    @NotNull(message = "查询天数为空")
    @Enumerable(ranges = {"7", "14", "30"}, message = "查询天数错误")
    private Integer days;

    public Pair<LocalDate, LocalDate> getStartAndEndDay() {
        LocalDate endDay   = LocalDate.now().minusDays(1);
        LocalDate startDay = endDay.minusDays(this.days);
        return Pair.of(startDay, endDay);
    }

}
