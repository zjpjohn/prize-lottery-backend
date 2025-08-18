package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgentIncomePo {

    private Long          id;
    private String        seqNo;
    private Long          invUid;
    private Long          userId;
    private Integer       amount;
    private Double        ratio;
    private Integer       channel;
    private LocalDateTime gmtCreate;

}
