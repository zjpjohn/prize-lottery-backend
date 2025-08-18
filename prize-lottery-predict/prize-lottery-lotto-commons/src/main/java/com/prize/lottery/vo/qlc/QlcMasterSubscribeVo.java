package com.prize.lottery.vo.qlc;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QlcMasterSubscribeVo {

    private Long          id;
    private Long          userId;
    private MasterValue   master;
    private StatHitValue  red3;
    private StatHitValue  red18;
    private StatHitValue  red22;
    private StatHitValue  kill3;
    private LocalDateTime timestamp;

}
