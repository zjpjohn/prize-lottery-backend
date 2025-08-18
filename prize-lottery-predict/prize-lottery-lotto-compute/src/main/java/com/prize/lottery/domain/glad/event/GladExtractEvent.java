package com.prize.lottery.domain.glad.event;

import com.prize.lottery.enums.LotteryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GladExtractEvent {

    private LotteryEnum lottery;

}
