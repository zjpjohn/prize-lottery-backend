package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpertIncomePo {

    private Long          id;
    private String        seqNo;
    private Long          userId;
    private Long          payId;
    private String        period;
    private String        type;
    private Integer       profit;
    private LocalDateTime gmtCreate;

}
