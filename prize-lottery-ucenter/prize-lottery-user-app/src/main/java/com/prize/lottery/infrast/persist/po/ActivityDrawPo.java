package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.RaffleState;
import com.prize.lottery.infrast.persist.value.RaffleCode;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ActivityDrawPo {

    private Long          id;
    private Long          activityId;
    private Long          userId;
    private LocalDate  day;
    private RaffleCode code;
    private Integer     times;
    private RaffleState state;
    private Integer     version;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
