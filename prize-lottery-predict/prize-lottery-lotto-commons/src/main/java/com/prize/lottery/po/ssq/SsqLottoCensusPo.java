package com.prize.lottery.po.ssq;

import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.po.share.BaseLottoCensus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SsqLottoCensusPo extends BaseLottoCensus {

    /**
     * 双色球字段渠道
     */
    private SsqChannel channel;

}
