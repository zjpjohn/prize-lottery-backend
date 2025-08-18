package com.prize.lottery.vo.dlt;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DltMasterSubscribeVo {

    private Long          id;
    private Long         userId;
    private MasterValue  master;
    private StatHitValue red3;
    private StatHitValue  red20;
    private StatHitValue  redKill;
    private StatHitValue  blueKill;
    private LocalDateTime timestamp;
}
