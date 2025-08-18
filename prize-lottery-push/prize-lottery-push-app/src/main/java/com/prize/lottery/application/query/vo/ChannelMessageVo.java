package com.prize.lottery.application.query.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChannelMessageVo {
    //渠道名称
    private String        name;
    //公告渠道值
    private String        channel;
    //渠道图片
    private String        cover;
    //渠道类型
    private Integer       type;
    //渠道描述
    private String        remark;
    //渠道是否提醒:0-不提醒,1-提醒
    private Integer       remind;
    //是否已读:0-已读,1-未读
    private Integer       read;
    //最新公告标题
    private String        title;
    //最新公告时间
    private LocalDateTime latestTime;
}
