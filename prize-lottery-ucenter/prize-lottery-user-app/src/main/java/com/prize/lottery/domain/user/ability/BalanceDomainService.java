package com.prize.lottery.domain.user.ability;

import com.prize.lottery.domain.user.event.CouponRewardEvent;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceDomainService {

    private final IUserBalanceRepository userBalanceRepository;

    /**
     * 积分奖励
     *
     * @param event 积分奖励领域事件
     */
    @EventListener
    public void couponAward(CouponRewardEvent event) {
        userBalanceRepository.ofId(event.getUserId()).ifPresent(balance -> {
            balance.rewardCoupon(event.getCoupon(), event.getRemark());
            userBalanceRepository.save(balance);
        });
    }

}
