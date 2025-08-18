package com.prize.lottery.vo.fc3d;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class Fc3dICaiGladVo {

    private Long         id;
    private String       period;
    private MasterValue  master;
    private StatHitValue dan3;
    private StatHitValue com6;
    private StatHitValue com7;
    private StatHitValue kill1;
}
