package com.prize.lottery.application.query.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class NotifyChartQuery {

    @NotNull(message = "应用key为空")
    private Long    appKey;
    @NotNull(message = "统计天数为空")
    @Enumerable(ranges = {"7", "14", "21", "28"}, message = "统计天数错误")
    private Integer days;

}
