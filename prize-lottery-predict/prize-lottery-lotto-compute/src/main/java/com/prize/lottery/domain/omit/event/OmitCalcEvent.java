package com.prize.lottery.domain.omit.event;

import com.prize.lottery.enums.LotteryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OmitCalcEvent {

    private LotteryEnum type;
    private String      period;

}
