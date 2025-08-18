package com.prize.lottery.po.qlc;

import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.po.share.BaseLottoCensus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QlcLottoCensusPo extends BaseLottoCensus {

    private QlcChannel channel;

}
