package com.prize.lottery.infrast.persist.vo;

import lombok.Data;

@Data
public class UserMetricsVo {

    /**
     * 昨日新增用户
     */
    private Integer yesterdayIncr;
    /**
     * 昨日活跃用户
     */
    private Integer yesterdayAct;
    /**
     * 昨日应用启动总次数
     */
    private Integer yesterdayLaunch;
    /**
     * 本周新增用户
     */
    private Integer weekIncr;
    /**
     * 本周活跃用户
     */
    private Integer weekAct;
    /**
     * 本周应用启动总次数
     */
    private Integer weekLaunch;
    /**
     * 本月新增用户
     */
    private Integer monthIncr;
    /**
     * 本月活跃用户
     */
    private Integer monthAct;
    /**
     * 本月应用启动次数
     */
    private Integer monthLaunch;
    /**
     * 上月新增用户
     */
    private Integer lastIncr;
    /**
     * 上月活跃用户数
     */
    private Integer lastAct;
    /**
     * 上月应用启动总次数
     */
    private Integer lastLaunch;
}
