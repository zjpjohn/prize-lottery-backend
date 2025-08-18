package com.prize.lottery.adapter.rpc;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.api.ICloudSmsService;
import com.prize.lottery.application.query.IMobileQueryService;
import com.prize.lottery.dto.SmsChannel;
import com.prize.lottery.dto.SmsCheckCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@Slf4j
@DubboService
@RequiredArgsConstructor
public class CloudSmsServiceProvider implements ICloudSmsService {

    private final IMobileQueryService mobileQueryService;

    @Override
    public boolean checkSmsCode(SmsCheckCmd command) {
        SmsChannel channel = SmsChannel.channel(command.getChannel());
        Assert.notNull(channel, ResponseHandler.MOBILE_SMS_CHANNEL_ERROR);
        return mobileQueryService.checkSmsCode(command.getCode(), command.getCode(), channel);
    }

}
