package com.prize.lottery.infrast.persist.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MetricsTimeDto {

    private LocalDate yesterday;
    private LocalDate weekStart;
    private LocalDate monthStart;
    private LocalDate lastStart;

    public MetricsTimeDto() {
        LocalDate now = LocalDate.now();
        this.yesterday  = now.minusDays(1);
        this.weekStart  = now.minusWeeks(1);
        this.monthStart = now.withDayOfMonth(1);
        this.lastStart  = now.minusMonths(1).withDayOfMonth(1);
    }

}
