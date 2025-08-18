package com.prize.lottery.po.lottery;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryAwardPo {

    private Long          id;
    /**
     * 彩票类型
     */
    private String        type;
    /**
     * 开奖期号
     */
    private String        period;
    /**
     * 本期销售总金额
     */
    private Long          sales;
    /**
     * 本期中奖总金额
     */
    private Long          award;
    /**
     * 下期奖池盈余
     */
    private Long          pool;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

}
