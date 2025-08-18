package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ExpertMetricsPo {

    private Long          id;
    private Long          userId;
    private LocalDate     day;
    private Integer       users;
    private Integer       amount;
    private LocalDateTime gmtCreate;

}
