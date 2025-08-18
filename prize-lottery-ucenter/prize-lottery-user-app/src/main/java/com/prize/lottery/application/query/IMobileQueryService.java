package com.prize.lottery.application.query;


import com.prize.lottery.application.query.vo.AuthPhoneNumber;
import com.prize.lottery.dto.SmsChannel;

public interface IMobileQueryService {

    Boolean checkAuthMobile(String phone, String nonceStr, String signature);

    AuthPhoneNumber getMobile(String token);

    Boolean checkSmsCode(String phone, String code, SmsChannel channel);

}
