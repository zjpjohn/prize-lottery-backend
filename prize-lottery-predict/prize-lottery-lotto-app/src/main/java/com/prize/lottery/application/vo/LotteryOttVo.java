package com.prize.lottery.application.vo;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.OmitValue;
import lombok.Data;

@Data
public class LotteryOttVo {

    private LotteryEnum type;
    private String      period;
    private OmitValue   bott;
    private OmitValue   sott;
    private OmitValue   gott;
    private String      lottery;

}
