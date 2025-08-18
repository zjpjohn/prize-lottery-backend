package com.prize.lottery.vo.dlt;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class DltICaiGladVo {

    private Long         id;
    private String       period;
    private MasterValue  master;
    private StatHitValue red3;
    private StatHitValue red20;
    private StatHitValue rk;
    private StatHitValue bk;
    private Integer      r20Hit;
    private Integer      b6Hit;
}
