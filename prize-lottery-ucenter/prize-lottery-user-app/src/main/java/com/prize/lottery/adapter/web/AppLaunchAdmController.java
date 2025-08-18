package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.IAppLaunchQueryService;
import com.prize.lottery.application.query.dto.AppLaunchQuery;
import com.prize.lottery.infrast.persist.po.AppDevicePo;
import com.prize.lottery.infrast.persist.po.AppLauncherPo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm")
@Permission(domain = LotteryAuth.MANAGER)
public class AppLaunchAdmController {

    private final IAppLaunchQueryService appLaunchQueryService;

    @GetMapping("/launch/list")
    public Page<AppLauncherPo> appLaunchList(@Validated AppLaunchQuery query) {
        return appLaunchQueryService.getUserAppLaunchList(query);
    }

    @GetMapping("/device/{deviceId}")
    public AppDevicePo appDevice(@NotBlank(message = "设备标识为空") @PathVariable String deviceId) {
        return appLaunchQueryService.getAppDevice(deviceId);
    }

    @GetMapping("/device/list/{userId}")
    public List<AppDevicePo> userDevices(@NotNull(message = "用户标识为空") @PathVariable Long userId) {
        return appLaunchQueryService.getUserDevices(userId);
    }

}
