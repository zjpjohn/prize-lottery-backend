package com.prize.lottery.application.query.vo;

import lombok.Data;

@Data
public class UserTotalMetricsVo {

    //总用户数
    private Integer totalUser;
    //总新增用户数
    private Integer totalIncr;
    //总启动应用次数
    private Integer totalLaunch;
    //总活跃用户数
    private Integer totalActive;

}
