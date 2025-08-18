package com.prize.lottery.vo;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.MasterValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterSubscribeVo {

    private Long          id;
    //专家标识
    private String        masterId;
    //关注类型
    private LotteryEnum   channel;
    //追踪字段值
    private String        trace;
    //追踪字段中文名
    private String        traceZh;
    //是否为重点关注
    private Integer       special;
    //最新预测期号
    private String        latest;
    //最新期更新时间
    private LocalDateTime modifyTime;
    //订阅专家时间
    private LocalDateTime gmtCreate;
    //专家信息
    private MasterValue   master;

}

