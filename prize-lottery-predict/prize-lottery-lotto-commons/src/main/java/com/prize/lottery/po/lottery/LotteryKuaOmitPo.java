package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.Omit;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryKuaOmitPo {

    private Long          id;
    private LotteryEnum   type;
    private String        period;
    private Omit          baseOmit;
    private Omit          ampOmit;
    private Omit          ampAmp;
    private Omit          ottOmit;
    private Omit          bmsOmit;
    private LocalDateTime gmtCreate;

}
