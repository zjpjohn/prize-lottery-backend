package com.prize.lottery.infrast.persist.vo;

import lombok.Data;

@Data
public class PushMetricsVo {

    /**
     * 应用标识
     */
    private Long    appKey;
    /**
     * 今日发送数量
     */
    private Integer todaySent;
    /**
     * 昨日发送数量
     */
    private Integer yesterdaySent;
    /**
     * 今日送达设备数
     */
    private Integer todayReceive;
    /**
     * 昨日送达设备数
     */
    private Integer yesterdayReceive;
    /**
     * 今日点击数量
     */
    private Integer todayOpened;
    /**
     * 昨日点击数量
     */
    private Integer yesterdayOpened;
    /**
     * 今日设备清除通知数量
     */
    private Integer todayDeleted;
    /**
     * 昨日设备清除通知数量
     */
    private Integer yesterdayDeleted;

}
