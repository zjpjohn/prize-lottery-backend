package com.prize.lottery.adapter.web;


import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.INotifyDeviceQueryService;
import com.prize.lottery.application.query.dto.NotifyDeviceQuery;
import com.prize.lottery.infrast.persist.po.NotifyDevicePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/adm/device")
@RequiredArgsConstructor
@Permission(domain = LotteryAuth.MANAGER)
public class DeviceAdmController {

    private final INotifyDeviceQueryService notifyDeviceQueryService;

    @GetMapping("/list")
    public Page<NotifyDevicePo> deviceList(@Validated NotifyDeviceQuery query) {
        return notifyDeviceQueryService.getNotifyDeviceList(query);
    }

}
