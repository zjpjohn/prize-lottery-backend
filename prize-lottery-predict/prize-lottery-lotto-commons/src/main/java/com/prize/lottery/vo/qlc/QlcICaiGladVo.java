package com.prize.lottery.vo.qlc;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class QlcICaiGladVo {

    private Long         id;
    private String       period;
    private MasterValue  master;
    private StatHitValue red3;
    private StatHitValue red18;
    private StatHitValue red22;
    private StatHitValue kill3;

}
