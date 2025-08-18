package com.prize.lottery.domain.feeds.event;

import com.prize.lottery.enums.LotteryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MasterFeedEvent {

    private final LotteryEnum type;
    private final String      masterId;
    private final String      period;

}
