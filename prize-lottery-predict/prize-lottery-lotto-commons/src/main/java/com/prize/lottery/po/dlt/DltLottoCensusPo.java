package com.prize.lottery.po.dlt;

import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.po.share.BaseLottoCensus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DltLottoCensusPo extends BaseLottoCensus {

    /**
     * 统计字段渠道
     */
    private DltChannel channel;

}
