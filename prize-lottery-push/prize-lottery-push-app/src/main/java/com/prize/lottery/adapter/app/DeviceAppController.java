package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.INotifyDeviceCommandService;
import com.prize.lottery.application.command.dto.DeviceCreateCmd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/app/device")
@RequiredArgsConstructor
@Permission(domain = LotteryAuth.USER)
public class DeviceAppController {

    private final INotifyDeviceCommandService notifyDeviceCommandService;

    @PostMapping("/")
    public void createDevice(@Validated DeviceCreateCmd command) {
        notifyDeviceCommandService.createDevice(command);
    }

}
