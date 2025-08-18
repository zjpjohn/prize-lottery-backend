package com.prize.lottery.vo;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HomeMasterVo {

    private Long          id;
    private String        period;
    private String      type;
    private MasterValue master;
    private Integer       rank;
    private StatHitValue  rate;
    private LocalDateTime gmtCreate;

}
