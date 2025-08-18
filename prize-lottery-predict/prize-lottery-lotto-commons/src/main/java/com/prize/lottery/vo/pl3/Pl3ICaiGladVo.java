package com.prize.lottery.vo.pl3;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class Pl3ICaiGladVo {

    private Long         id;
    private String       period;
    private MasterValue  master;
    private StatHitValue dan3;
    private StatHitValue com6;
    private StatHitValue com7;
    private StatHitValue kill1;

}
