package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.RaffleState;
import com.prize.lottery.infrast.persist.value.RaffleCode;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityChancePo {

    private Long          id;
    private Long       drawId;
    private RaffleCode code;
    private Integer       type;
    private RaffleState   state;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
