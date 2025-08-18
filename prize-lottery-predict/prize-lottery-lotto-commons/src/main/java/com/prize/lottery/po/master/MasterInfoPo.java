package com.prize.lottery.po.master;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterInfoPo {

    private Long          id;
    //专家唯一标识
    private String        seq;
    //专家头像
    private String        name;
    //专家手机号
    private String        phone;
    //专家地址
    private String        address;
    //专家头像
    private String        avatar;
    //来源渠道
    private Integer       source;
    //来源标识
    private String        sourceId;
    //浏览量
    private Integer       browse;
    //订阅量
    private Integer       subscribe;
    //搜索量
    private Integer       searches;
    //开启预测渠道集合
    private Byte          enable;
    //专家描述
    private String        description;
    //专家状态
    private Integer       state;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
