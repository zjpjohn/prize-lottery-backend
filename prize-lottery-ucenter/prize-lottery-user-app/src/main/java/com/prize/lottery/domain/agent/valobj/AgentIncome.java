package com.prize.lottery.domain.agent.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgentIncome {

    private String  seqNo;
    private Long    invUid;
    private Long    userId;
    private Integer amount;
    private Double  ratio;
    private Integer channel;

}
