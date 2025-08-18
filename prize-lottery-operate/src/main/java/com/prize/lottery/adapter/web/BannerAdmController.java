package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IAppBannerCommandService;
import com.prize.lottery.application.command.dto.BannerCreateCmd;
import com.prize.lottery.application.command.dto.BannerModifyCmd;
import com.prize.lottery.application.query.IAppBannerQueryService;
import com.prize.lottery.application.query.dto.AppBannerQuery;
import com.prize.lottery.infrast.persist.po.AppBannerPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/adm/banner")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class BannerAdmController {

    private final IAppBannerCommandService appBannerCommandService;
    private final IAppBannerQueryService   appBannerQueryService;

    @PostMapping("/")
    public void createBanner(@RequestBody @Validated BannerCreateCmd cmd) {
        appBannerCommandService.createBanner(cmd);
    }

    @PutMapping("/")
    public void editBanner(@RequestBody @Validated BannerModifyCmd cmd) {
        appBannerCommandService.editBanner(cmd);
    }

    @GetMapping("/{id}")
    public AppBannerPo appBanner(@PathVariable Long id) {
        return appBannerQueryService.getAppBanner(id);
    }

    @GetMapping("/list")
    public Page<AppBannerPo> bannerList(@Validated AppBannerQuery query) {
        return appBannerQueryService.getAppBannerList(query);
    }

}
