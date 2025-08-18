package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.Omit;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LottoPianOmitPo {

    private Long          id;
    private LotteryEnum   type;
    private String        period;
    private Integer       level;
    private Omit          omit;
    private Omit          cb1;
    private Omit          cb2;
    private Omit          cb3;
    private LocalDateTime gmtCreate;

}
