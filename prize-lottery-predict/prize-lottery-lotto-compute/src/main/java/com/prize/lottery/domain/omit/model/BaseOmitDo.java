package com.prize.lottery.domain.omit.model;

import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;

@Data
public abstract class BaseOmitDo {

    private LotteryEnum type;

    public abstract LotteryOmitDo toOmit();
}
