package com.prize.lottery.po.kl8;

import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.po.share.BaseLottoCensus;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class Kl8LottoCensusPo extends BaseLottoCensus {

    /**
     * 统计数据来源类型
     */
    private Kl8Channel channel;

}
