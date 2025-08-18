package com.prize.lottery.infrast.persist.vo;

import lombok.Data;

@Data
public class DeviceMetricsVo {

    /**
     * 昨日设备总数
     */
    private Integer yesterdayCnt;
    /**
     * 昨日新增设备数
     */
    private Integer yesterdayIncr;

    /**
     * 本周新增设备数
     */
    private Integer weekIncr;
    /**
     * 本月新增设备数
     */
    private Integer monthIncr;
    /**
     * 上月设备总数
     */
    private Integer lastCnt;
    /**
     * 上月新增设备数
     */
    private Integer lastIncr;

}
