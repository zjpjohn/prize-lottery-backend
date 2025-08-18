package com.prize.lottery.application.query.vo;

import lombok.Data;

@Data
public class ChargeConfVo {

    private Long    id;
    private String  name;
    private Long    amount;
    private Long    gift;
    private Integer priority;

}
