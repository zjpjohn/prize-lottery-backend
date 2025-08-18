package com.prize.lottery.po.pl3;

import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.po.share.BaseLottoCensus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Pl3LottoCensusPo extends BaseLottoCensus {

    /**
     * 排列三字段渠道
     */
    private Pl3Channel channel;

}
