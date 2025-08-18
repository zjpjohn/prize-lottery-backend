package com.prize.lottery.domain.omit.model;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.OmitValue;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LotteryOttDo {

    private Long        id;
    private LotteryEnum type;
    private String      period;
    private OmitValue   bott;
    private OmitValue   sott;
    private OmitValue   gott;

    public LotteryOttDo(LotteryEnum type, String period, List<Integer> balls) {
        this.type   = type;
        this.period = period;
        this.bott   = calcOtt(balls.get(0));
        this.sott   = calcOtt(balls.get(1));
        this.gott   = calcOtt(balls.get(2));
    }

    private OmitValue calcOtt(Integer ball) {
        int mode = ball % 3;
        return OmitValue.of(Integer.toString(mode), 0);
    }
}
