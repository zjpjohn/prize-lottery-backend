package com.prize.lottery.api;


import com.prize.lottery.dto.SmsCheckCmd;

public interface ICloudSmsService {

    /**
     * 校验短信验证码
     */
    boolean checkSmsCode(SmsCheckCmd checkCmd);
}
