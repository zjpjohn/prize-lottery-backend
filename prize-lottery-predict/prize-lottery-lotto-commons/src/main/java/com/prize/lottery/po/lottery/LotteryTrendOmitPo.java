package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.Omit;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryTrendOmitPo {

    private Long          id;
    private LotteryEnum   type;
    private String        period;
    private String        balls;
    private Omit          formOmit;
    private Omit          ottOmit;
    private Omit          modeOmit;
    private Omit          bsOmit;
    private Omit          oeOmit;
    private LocalDateTime gmtCreate;

}
