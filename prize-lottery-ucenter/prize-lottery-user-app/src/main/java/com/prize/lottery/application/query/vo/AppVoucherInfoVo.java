package com.prize.lottery.application.query.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppVoucherInfoVo {

    //代金券标识
    private String        seqNo;
    //代金券名称
    private String        name;
    //代金券描述
    private String        remark;
    //代金券金额
    private Integer       voucher;
    //是否为一次性领取:0-否,1-是
    private Integer       disposable;
    //是否过期
    private Integer       expire;
    //多次领取间隔天数
    private Integer       interval;
    //当前代金券是否可领取
    private boolean       canDraw = false;
    //上一次领取时间
    private LocalDateTime lastDraw;
    //下一次可领取开始时间
    private LocalDateTime nextDraw;

}
