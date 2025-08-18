package com.prize.lottery.vo.kl8;

import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.value.CensusValue;
import lombok.Data;

@Data
public class Kl8FullCensusVo {

    private String      period;
    /**
     * 统计字段渠道
     */
    private Kl8Channel  channel;
    /**
     * 前80名统计
     */
    private CensusValue level80;
    /**
     * 前120名统计
     */
    private CensusValue level120;
    /**
     * 前160名统计
     */
    private CensusValue level160;
    /**
     * 前200名统计
     */
    private CensusValue level200;
    /**
     * 前320名统计
     */
    private CensusValue level320;

}
