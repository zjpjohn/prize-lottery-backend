package com.prize.lottery.domain.expert.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpertIncome {

    private String  seqNo;
    private Long    userId;
    private Long    payerId;
    private String  period;
    private String  type;
    private Long profit;

}
