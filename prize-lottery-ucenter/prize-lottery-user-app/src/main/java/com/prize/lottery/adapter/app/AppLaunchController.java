package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.utils.RequestUtils;
import com.prize.lottery.application.command.ILauncherCommandService;
import com.prize.lottery.application.command.dto.AppLauncherCmd;
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
@RequiredArgsConstructor
@RequestMapping("/app/launch")
public class AppLaunchController {

    private final ILauncherCommandService launcherCommandService;

    @PostMapping()
    public void appLaunch(@Validated AppLauncherCmd command) {
        launcherCommandService.appLaunch(command, RequestUtils.ipAddress());
    }

}
