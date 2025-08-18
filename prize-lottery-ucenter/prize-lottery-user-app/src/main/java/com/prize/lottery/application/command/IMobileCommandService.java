package com.prize.lottery.application.command;


import com.cloud.arch.mobile.verify.VerifyResult;
import com.prize.lottery.application.command.dto.SmsSendCmd;
import com.prize.lottery.application.command.dto.VerifyPhoneCmd;

public interface IMobileCommandService {

    void sendSms(SmsSendCmd command);

    VerifyResult verify(VerifyPhoneCmd command);

}
