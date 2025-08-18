package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.HoneyValue;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LotteryHoneyPo {

    private Long          id;
    private String        period;
    private LotteryEnum   type;
    private HoneyValue    honey;
    private LocalDate     lottoDate;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
