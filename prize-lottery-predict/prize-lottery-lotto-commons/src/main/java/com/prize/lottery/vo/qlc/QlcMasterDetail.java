package com.prize.lottery.vo.qlc;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QlcMasterDetail {

    private Long          id;
    private String        period;
    private MasterValue   master;
    private StatHitValue  red3;
    private StatHitValue  red18;
    private StatHitValue  red22;
    private StatHitValue  kill3;
    private Integer       vip        = 0;
    private Integer       modified   = 0;
    private String        trace;
    private String        traceZh;
    private Integer       special    = 0;
    private Integer       subscribed = 0;
    private LocalDateTime timestamp;

}
