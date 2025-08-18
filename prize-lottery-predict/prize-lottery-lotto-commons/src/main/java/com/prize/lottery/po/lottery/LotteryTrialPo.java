package com.prize.lottery.po.lottery;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryTrialPo {
    /**
     * 主键
     */
    private Long          id;
    /**
     * 彩票类型
     */
    private String        type;
    /**
     * 开试机号期号
     */
    private String        period;
    /**
     * 开机号
     */
    private String        kai;
    /**
     * 试机号
     */
    private String        shi;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
}
