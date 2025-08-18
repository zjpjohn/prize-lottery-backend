package com.prize.lottery.application.command.impl;

import com.cloud.arch.mobile.sms.CloudSmsExecutor;
import com.cloud.arch.mobile.sms.SmsParam;
import com.cloud.arch.mobile.verify.VerifyMobileExecutor;
import com.cloud.arch.mobile.verify.VerifyResult;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.error.ApiBizException;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.IMobileCommandService;
import com.prize.lottery.application.command.dto.SmsSendCmd;
import com.prize.lottery.application.command.dto.VerifyPhoneCmd;
import com.prize.lottery.domain.mobile.domain.CloudSmsDo;
import com.prize.lottery.infrast.props.MobileSmsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MobileCommandService implements IMobileCommandService {

    private final MobileSmsProperties  properties;
    private final CloudSmsExecutor     cloudSmsExecutor;
    private final VerifyMobileExecutor verifyMobileExecutor;

    /**
     * 校验手机号
     */
    @Override
    public VerifyResult verify(VerifyPhoneCmd command) {
        String bizId = String.valueOf(IdWorker.nextId());
        return verifyMobileExecutor.verify(command.getCode(), command.getPhone(), bizId);
    }

    @Override
    public void sendSms(SmsSendCmd command) {
        try {
            String template = properties.getTemplates().get(command.getChannel());
            Assert.state(StringUtils.isNotBlank(template));
            CloudSmsDo smsDo    = new CloudSmsDo(command.getPhone());
            SmsParam   smsParam = smsDo.buildParam(properties.getSign(), template);
            cloudSmsExecutor.asyncSend(smsParam, command.getChannel(), properties.getExpire());
        } catch (Exception e) {
            log.error("发送短信验证码失败:", e);
            throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "发送短信失败");
        }
    }

}
