package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.CodeType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.CodePosition;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryCodePo {

    private Long          id;
    private String        period;
    private LotteryEnum   lotto;
    private CodeType      type;
    private CodePosition  position;
    private LocalDateTime gmtCreate;

}

