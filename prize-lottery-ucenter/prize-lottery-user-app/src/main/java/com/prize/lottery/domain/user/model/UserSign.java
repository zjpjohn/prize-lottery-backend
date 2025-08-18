package com.prize.lottery.domain.user.model;

import com.cloud.arch.event.publisher.DomainEventPublisher;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.user.event.CouponRewardEvent;
import com.prize.lottery.domain.user.valobj.UserSignLog;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.SignType;
import com.prize.lottery.infrast.props.UserSignProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
public class UserSign {

    /* 签到用户标识 */
    private Long        userId;
    /* 当前签到日期 */
    private LocalDate   current   = LocalDate.now();
    /* 上一次签到时间 */
    private LocalDate   lastDate  = null;
    /* 连续签到天数 */
    private Integer     series    = 0;
    /* 累计签到次数 */
    private Integer     times     = 0;
    /* 累计签到积分 */
    private Integer     coupon    = 0;
    /* 是否已签到:0-否,1-是 */
    private Integer     hasSigned = 0;
    /* 签到日志  */
    private UserSignLog log       = null;

    public UserSign(Long userId) {
        this.userId = userId;
    }

    /**
     * 计算签到连续次数
     *
     * @param props 签到规则参数
     */
    private int calcSignSeries(UserSignProperties props) {
        //连续签到且今日签到未超过门槛日期
        boolean isSeriesSigned = this.lastDate != null
                && this.lastDate.equals(this.current.minusDays(1))
                && this.series < props.getThrottle();
        return isSeriesSigned ? this.series + 1 : 1;
    }

    /**
     * 根据签到规则签到
     *
     * @param props 签到规则
     */
    public void signAndFire(UserSignProperties props) {
        Assert.state(lastDate == null || current.isAfter(lastDate), ResponseHandler.USER_HAS_SIGNED);
        //连续签到计算
        this.series = this.calcSignSeries(props);
        //签到奖励计算
        int      awardCoupon = props.getEvery();
        SignType signType    = SignType.EVERY;
        //当前签到已经达到签到门槛规则
        if (this.series >= props.getThrottle()) {
            signType    = SignType.SERIES;
            awardCoupon = props.getSeries();
        }
        this.times    = times + 1;
        this.lastDate = this.current;
        this.coupon   = this.coupon + awardCoupon;
        //签到日志记录
        this.log = new UserSignLog();
        this.log.setUserId(this.userId);
        this.log.setType(signType);
        this.log.setAward(awardCoupon);
        this.log.setSignTime(LocalDateTime.now());
        //发送积分奖励事件
        CouponRewardEvent rewardEvent = new CouponRewardEvent(this.userId, awardCoupon, "签到积分奖励");
        DomainEventPublisher.publish(rewardEvent);
    }

    /**
     * 签到计算
     */
    public void calcSigned(UserSignProperties props) {
        if (lastDate == null) {
            return;
        }
        long delta = ChronoUnit.DAYS.between(lastDate, current);
        this.hasSigned = delta == 0 ? 1 : 0;
        if (delta > 1 || (delta == 1 && this.series >= props.getThrottle())) {
            this.series = 0;
        }
    }
}
