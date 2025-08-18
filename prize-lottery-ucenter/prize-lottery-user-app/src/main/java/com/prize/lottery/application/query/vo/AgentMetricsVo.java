package com.prize.lottery.application.query.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AgentMetricsVo {

    private LocalDate day;
    private Integer   users   = 0;
    private Integer   amount  = 0;
    private Integer   invites = 0;

    public AgentMetricsVo(LocalDate day) {
        this.day = day;
    }

}
