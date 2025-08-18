package com.prize.lottery.vo;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.MasterValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterGladVo {

    private Long          id;
    private String        period;
    private MasterValue   master;
    private LotteryEnum   lottery;
    private String        content;
    private Integer       type;
    private LocalDateTime gmtCreate;

}
