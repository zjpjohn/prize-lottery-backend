package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.OmitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryOttPo {

    private Long        id;
    private LotteryEnum type;
    private String      period;
    private OmitValue bott;
    private OmitValue sott;
    private OmitValue     gott;
    private LocalDateTime gmtCreate;

}
