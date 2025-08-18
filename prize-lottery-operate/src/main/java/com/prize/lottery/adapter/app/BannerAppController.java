package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.IAppBannerQueryService;
import com.prize.lottery.infrast.persist.po.AppBannerPo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@RestController
@RequestMapping("/app/banner")
@Permission(domain = LotteryAuth.USER)
@RequiredArgsConstructor
public class BannerAppController {

    private final IAppBannerQueryService appBannerQueryService;

    @GetMapping("/")
    public AppBannerPo appBanner(@NotBlank(message = "应用标识为空") String appNo,
                                 @NotBlank(message = "所属页面为空") String page) {
        return appBannerQueryService.getAppBanner(appNo, page);
    }

}
