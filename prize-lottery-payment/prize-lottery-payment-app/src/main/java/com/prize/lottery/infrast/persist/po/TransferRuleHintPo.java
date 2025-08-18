package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferRuleHintPo {

    private Long          id;
    private String        scene;
    private String        hint;
    private Integer       state;
    private LocalDateTime startTime;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
