package com.prize.lottery.infrast.persist.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AgentMetricsPo {

    private Long          id;
    private Long          userId;
    private LocalDate     day;
    private Integer       users;
    private Integer       amount;
    private Integer       invites;
    private LocalDateTime gmtCreate;

    public AgentMetricsPo(Long userId, LocalDate day) {
        this.userId  = userId;
        this.day     = day;
        this.users   = 0;
        this.amount  = 0;
        this.invites = 0;
    }
}
