package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChargeConfPo {

    private Long          id;
    private String        name;
    private Long          amount;
    private Long          gift;
    private Integer     priority;
    private CommonState state;
    private Integer     version;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
