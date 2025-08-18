package com.prize.lottery.vo;

import com.prize.lottery.value.MasterValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterFeedRateVo {

    private String        masterId;
    private String        field;
    private MasterValue   master;
    private LocalDateTime timestamp;
    private String        period;
    private Double        rate;
    private String        hitCount;
    private Integer       hit;

}
