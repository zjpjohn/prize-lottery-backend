package com.prize.lottery.po.lottery;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryLevelPo {

    /**
     * 主键
     */
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
     * 中奖等级
     */
    private Integer       level;
    /**
     * 中奖注数
     */
    private Integer       quantity;
    /**
     * 单注奖金
     */
    private Double        bonus;
    /**
     * 派奖总金额
     */
    private Double        amount;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
}
