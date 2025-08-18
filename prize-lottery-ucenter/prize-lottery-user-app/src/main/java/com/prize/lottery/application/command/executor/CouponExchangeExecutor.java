package com.prize.lottery.application.command.executor;

import com.prize.lottery.application.command.vo.ExchangeResult;
import com.prize.lottery.domain.user.model.UserBalance;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CouponExchangeProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponExchangeExecutor {

    private final CouponExchangeProperties properties;
    private final IUserBalanceRepository   userBalanceRepository;

    @Transactional
    public ExchangeResult execute(Long userId) {
        UserBalance userBalance = userBalanceRepository.ofId(userId).orElseThrow(ResponseHandler.ACCOUNT_NONE_EXISTED);
        //积分兑换奖励金
        userBalance.exchangeCoupon(properties);
        userBalanceRepository.save(userBalance);

        ExchangeResult result = new ExchangeResult();
        //兑换积分消耗的积分(消耗积分为负数故需要乘以-1)
        result.setCoupon(-1 * userBalance.getOperation().getCoupon());
        //兑换获得奖励金
        result.setSurplus(userBalance.getOperation().getSurplus());
        //兑换后剩余的积分
        result.setRemain(userBalance.getCoupon());
        return result;
    }

}
