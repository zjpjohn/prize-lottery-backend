package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.Omit;
import com.prize.lottery.value.OmitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryMatchOmitPo {

    private Long          id;
    private LotteryEnum   type;
    private String        period;
    private Omit          codeOmit;
    private Omit          comOmit;
    private OmitValue     allOmit;
    private LocalDateTime gmtCreate;

}
