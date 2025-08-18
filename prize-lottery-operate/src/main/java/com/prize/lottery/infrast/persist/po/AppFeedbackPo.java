package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.FeedbackState;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AppFeedbackPo {

    private Long          id;
    //应用编号
    private String        appNo;
    //应用版本信息
    private String        appVersion;
    //反馈设备信息
    private String        device;
    //反馈类型
    private String        type;
    //反馈内容
    private String        content;
    //反馈图片集合
    private List<String>  images;
    //反馈处理状态
    private FeedbackState state;
    //反馈备注
    private String        remark;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;
}
