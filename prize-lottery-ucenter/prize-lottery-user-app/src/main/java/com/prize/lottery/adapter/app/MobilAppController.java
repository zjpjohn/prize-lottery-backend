package com.prize.lottery.adapter.app;

import com.cloud.arch.mobile.verify.VerifyResult;
import com.cloud.arch.web.annotation.ApiBody;
import com.prize.lottery.application.command.IMobileCommandService;
import com.prize.lottery.application.command.dto.SmsSendCmd;
import com.prize.lottery.application.command.dto.VerifyPhoneCmd;
import com.prize.lottery.application.query.IMobileQueryService;
import com.prize.lottery.application.query.vo.AuthPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/mobile")
@RequiredArgsConstructor
public class MobilAppController {

    private final IMobileCommandService mobileCommandService;
    private final IMobileQueryService   mobileQueryService;

    @GetMapping("/")
    public AuthPhoneNumber getMobile(@NotBlank(message = "token不允许为空") String token) {
        return mobileQueryService.getMobile(token);
    }

    @GetMapping("/verify")
    public VerifyResult verify(@Validated VerifyPhoneCmd cmd) {
        return mobileCommandService.verify(cmd);
    }

    @PostMapping("/sms")
    public void sendSms(@Validated SmsSendCmd cmd) {
        mobileCommandService.sendSms(cmd);
    }

}
