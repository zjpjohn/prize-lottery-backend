package com.prize.lottery.application.query.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class OrderMetricsTrendVo {

    private LocalDate           day;
    private TrendValue<Long>    successAmt;
    private TrendValue<Integer> successCnt;
    private TrendValue<Long>    failureAmt;
    private TrendValue<Integer> failureCnt;
    private TrendValue<Long>    closedAmt;
    private TrendValue<Integer> closedCnt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrendValue<T> {

        private T       value;
        private Integer trend;

    }

}
