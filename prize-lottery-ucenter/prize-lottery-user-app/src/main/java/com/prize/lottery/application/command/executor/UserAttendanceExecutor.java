package com.prize.lottery.application.command.executor;

import com.prize.lottery.application.assembler.UserInfoAssembler;
import com.prize.lottery.application.command.vo.UserSignResult;
import com.prize.lottery.application.command.vo.UserSignVo;
import com.prize.lottery.domain.user.model.UserSign;
import com.prize.lottery.domain.user.repository.IUserSignRepository;
import com.prize.lottery.infrast.props.CouponExchangeProperties;
import com.prize.lottery.infrast.props.UserSignProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAttendanceExecutor {

    private final UserSignProperties  properties;
    private final UserInfoAssembler   userInfoAssembler;
    private final IUserSignRepository      userSignRepository;
    private final CouponExchangeProperties exchangeProperties;

    /**
     * 签到操作
     */
    @Transactional
    public UserSignResult userSign(Long userId) {
        UserSign userSign = userSignRepository.ofId(userId);
        //签到并发送积分奖励事件
        userSign.signAndFire(properties);
        userSignRepository.save(userSign);

        UserSignResult result = userInfoAssembler.toResult(userSign, userSign.getLog(), this.properties);
        result.setRule(exchangeProperties);
        return result;
    }

    /**
     * 计算账户签到信息
     */
    public UserSignVo getUserSign(Long userId) {
        UserSign userSign = userSignRepository.ofId(userId);
        userSign.calcSigned(properties);

        UserSignVo result = userInfoAssembler.toVo(userSign, this.properties);
        result.setRule(exchangeProperties);
        return result;
    }

}
