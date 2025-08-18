package com.prize.lottery.infrast.event;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsCalcEvent implements Event<StatsCalcEvent> {

    private LotteryEnum type;
    private String      period;

    @Override
    public void assign(StatsCalcEvent event) {
        this.type   = event.getType();
        this.period = event.getPeriod();
    }

}
