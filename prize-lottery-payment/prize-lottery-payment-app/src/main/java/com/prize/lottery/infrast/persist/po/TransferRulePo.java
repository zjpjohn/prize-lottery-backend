package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.RuleState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferRulePo {

    private Long          id;
    private String        scene;
    private Long          throttle;
    private Integer   force;
    private RuleState state;
    private String    remark;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
