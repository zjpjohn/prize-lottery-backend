package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryFairTrialPo {

    private Long          id;
    private LotteryEnum   type;
    private String        period;
    private String        ball;
    private LocalDateTime gmtCreate;

}
