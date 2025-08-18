package com.prize.lottery.value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Period {

    //预测期号
    private String  period;
    //预测期号是否已开奖
    private Integer opened;
    //预测数期号是否已计算
    private Integer calculated;

    public Period(String period) {
        this.period = period;
    }

    public void calculated() {
        this.opened     = 1;
        this.calculated = 1;
    }

    public boolean isOpened() {
        return opened != null && opened == 1;
    }

    public boolean isCalculated() {
        return calculated != null && calculated == 1;
    }

}
