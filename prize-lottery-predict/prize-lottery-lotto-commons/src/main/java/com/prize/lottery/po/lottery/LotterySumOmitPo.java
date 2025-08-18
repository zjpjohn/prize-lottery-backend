package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.Omit;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotterySumOmitPo {

    private Long          id;
    private String        period;
    private LotteryEnum   type;
    private Omit          baseOmit;
    private Omit          ottOmit;
    private Omit          tailOmit;
    private Omit          tailAmp;
    private Omit          bmsOmit;
    private LocalDateTime gmtCreate;

}
