package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserStatisticsPo {

    private Long          id;
    /**
     * 统计日期
     */
    private LocalDate     day;
    /**
     * 日注册量
     */
    private Integer       register;
    /**
     * 日活用户量
     */
    private Integer       active;
    /**
     * 日启动应用总数
     */
    private Integer       launch;
    /**
     * 日平均启动次数
     */
    private Integer       launchAvg;
    /**
     * 日邀请用户总数
     */
    private Integer       invite;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;
}
