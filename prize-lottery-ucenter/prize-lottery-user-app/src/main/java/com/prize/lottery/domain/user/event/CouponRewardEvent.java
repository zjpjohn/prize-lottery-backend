package com.prize.lottery.domain.user.event;

import com.cloud.arch.event.annotations.Publish;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Publish
@AllArgsConstructor
public class CouponRewardEvent {

    private Long    userId;
    private Integer coupon;
    private String  remark;

}
