package com.prize.lottery.infrast.event;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculatorEvent implements Event<CalculatorEvent> {

    private LotteryEnum type;
    private String      period;

    @Override
    public void assign(CalculatorEvent event) {
        this.type   = event.getType();
        this.period = event.getPeriod();
    }
}
