package com.prize.lottery.vo.ssq;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SsqMasterSubscribeVo {

    private Long          id;
    private Long         userId;
    private MasterValue  master;
    private StatHitValue red3;
    private StatHitValue red25;
    private StatHitValue  rk3;
    private StatHitValue  bk;
    private LocalDateTime gmtCreate;
}
