package com.prize.lottery.application.query.impl;

import com.cloud.arch.encrypt.AESKit;
import com.cloud.arch.mobile.sms.SmsFlowController;
import com.cloud.arch.mobile.verify.GetMobileExecutor;
import com.cloud.arch.redis.RedissonTemplate;
import com.cloud.arch.utils.IdWorker;
import com.prize.lottery.application.query.IMobileQueryService;
import com.prize.lottery.application.query.vo.AuthPhoneNumber;
import com.prize.lottery.domain.user.model.UserInfo;
import com.prize.lottery.dto.SmsChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MobileQueryService implements IMobileQueryService {

    public static final String MOBILE_PREFIX = "t:m:c:";

    private final GetMobileExecutor getMobileExecutor;
    private final RedissonTemplate  redissonTemplate;
    private final SmsFlowController smsFlowController;

    @Override
    public Boolean checkAuthMobile(String phone, String nonceStr, String signature) {
        //校验手机号是否有效
        String encode = AESKit.CBC.pkc7Enc(phone + nonceStr, UserInfo.AES_KEY, UserInfo.AES_IV);
        if (!encode.equals(signature)) {
            return false;
        }
        //手机号是否存在
        Integer exist = redissonTemplate.get(MOBILE_PREFIX + phone);
        return exist != null && exist == 1;
    }

    @Override
    public AuthPhoneNumber getMobile(String token) {
        String uuid   = String.valueOf(IdWorker.nextId());
        String mobile = getMobileExecutor.getMobile(token, uuid);
        redissonTemplate.set(MOBILE_PREFIX + mobile, 1, 600L);
        return AuthPhoneNumber.of(mobile, uuid);
    }

    @Override
    public Boolean checkSmsCode(String phone, String code, SmsChannel channel) {
        return smsFlowController.checkCode(phone, channel.getChannel(), code);
    }
}
