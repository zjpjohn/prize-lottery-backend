package com.prize.lottery.po.lottery;

import com.prize.lottery.value.Omit;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryKl8OmitPo {

    private Long          id;
    private String        period;
    private Omit          ballOmit;
    private Omit          bsOmit;
    private Omit          oeOmit;
    private Omit          kuaOmit;
    private Omit          sumOmit;
    private Omit          tailOmit;
    private LocalDateTime gmtCreate;

}
