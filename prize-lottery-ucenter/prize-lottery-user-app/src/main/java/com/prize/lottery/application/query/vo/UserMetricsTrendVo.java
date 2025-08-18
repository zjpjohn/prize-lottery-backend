package com.prize.lottery.application.query.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class UserMetricsTrendVo {

    private LocalDate  day;
    private TrendValue incr;
    private TrendValue active;
    private TrendValue launch;
    private TrendValue launchAvg;
    private TrendValue invite;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrendValue {

        private Integer value;
        private Integer trend;

    }
}
