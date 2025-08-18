package com.prize.lottery.vo;

import com.prize.lottery.enums.LotteryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Num3LayerStateVo {

    private LotteryEnum type;
    private String      period;
    private Integer     state;

    public static Num3LayerStateVo empty(LotteryEnum type) {
        return new Num3LayerStateVo(type, "", 0);
    }

}
