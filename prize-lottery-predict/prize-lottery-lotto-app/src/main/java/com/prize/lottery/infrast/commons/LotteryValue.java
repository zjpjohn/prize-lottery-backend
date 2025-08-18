package com.prize.lottery.infrast.commons;

import com.prize.lottery.po.lottery.LotteryInfoPo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LotteryValue {

    private final String period;
    private final String red;
    private final String blue;

    public static LotteryValue of(LotteryInfoPo lottery) {
        return new LotteryValue(lottery.getPeriod(), lottery.getRed(), lottery.getBlue());
    }

}
