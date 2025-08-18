package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.prize.lottery.application.query.IAppInfoQueryService;
import com.prize.lottery.application.vo.AppInfoMobileVo;
import com.prize.lottery.infrast.persist.po.AppContactPo;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/native/app")
public class AppInfoController {

    private final IAppInfoQueryService appInfoQueryService;

    @GetMapping("/{appNo}/{version}")
    public AppInfoMobileVo appInfo(@NotBlank(message = "应用标识为空") @PathVariable String appNo,
                                   @NotBlank(message = "应用版本为空") @PathVariable String version) {
        return appInfoQueryService.getMobileAppInfo(appNo, version);
    }

    @GetMapping("/contact/list")
    public List<AppContactPo> usingContacts(@NotBlank(message = "应用标识为空") String appNo) {
        return appInfoQueryService.usingContacts(appNo);
    }

}
