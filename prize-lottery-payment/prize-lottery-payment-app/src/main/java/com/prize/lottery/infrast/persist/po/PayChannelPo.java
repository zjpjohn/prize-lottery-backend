package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.pay.PayChannel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PayChannelPo {

    private Long          id;
    //支付渠道标识码
    private String        seq;
    //支付渠道名称
    private String     name;
    //支付渠道值
    private PayChannel channel;
    //支付渠道图片
    private String     cover;
    //支付渠道本地图标
    private String        icon;
    //支付渠道描述
    private String        remark;
    //支付渠道优先级
    private Integer       priority;
    //是否支持支付
    private Integer       pay;
    //是否支持提现
    private Integer       withdraw;
    //创建时间
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
