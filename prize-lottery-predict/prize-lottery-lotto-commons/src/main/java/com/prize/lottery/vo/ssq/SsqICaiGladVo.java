package com.prize.lottery.vo.ssq;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class SsqICaiGladVo {

    private Long         id;
    private String       period;
    private MasterValue  master;
    private StatHitValue red3;
    private StatHitValue red25;
    private StatHitValue rk3;
    private StatHitValue bk;
    private Integer      r25Hit;
    private Integer      b5Hit;
}
