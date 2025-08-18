package com.prize.lottery.po.dlt;

import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DltHomeMasterPo {

    private Long          id;
    private String        period;
    private String     masterId;
    private DltChannel type;
    private Integer       rank;
    private StatHitValue  rate;
    private LocalDateTime gmtCreate;

}
