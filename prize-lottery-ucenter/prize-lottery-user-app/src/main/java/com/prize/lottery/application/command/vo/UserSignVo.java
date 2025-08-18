package com.prize.lottery.application.command.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prize.lottery.infrast.props.CouponExchangeProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserSignVo {

    private Long         userId;
    //每天签到积分
    private int          ecoupon;
    //连续签到达到门槛时的奖励积分
    private int          scoupon;
    //账户积分
    private Integer      acctCoupon  = 0;
    //连续签到门槛
    private int          throttle    = 0;
    //连续签到次数
    private int          series      = 0;
    //签到总次数
    private int          times       = 0;
    //累计签到总积分
    private int          coupon      = 0;
    //当前账户积分
    private int          current     = 0;
    //今日是否签到:0-未签到,1-已签到
    private int          hasSigned   = 0;
    //上一次签到时间
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private LocalDate    lastDate    = null;
    //兑换规则
    private ExchangeRule exchangeRule;

    /**
     * 设计兑换规则
     */
    public void setRule(CouponExchangeProperties properties) {
        exchangeRule = new ExchangeRule(properties.getRatio(), properties.getThrottle());
    }

    @Data
    public static class ExchangeRule {

        //积分兑换比例
        private Integer ratio;
        //积分兑换门槛
        private Integer throttle;

        public ExchangeRule(Integer ratio, Integer throttle) {
            this.ratio    = ratio;
            this.throttle = throttle;
        }
    }
}
