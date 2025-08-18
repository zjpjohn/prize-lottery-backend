package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IAppInfoCommandService;
import com.prize.lottery.application.command.dto.AppInfoCreateCmd;
import com.prize.lottery.application.command.dto.AppInfoModifyCmd;
import com.prize.lottery.application.command.dto.VersionCreateCmd;
import com.prize.lottery.application.command.dto.VersionModifyCmd;
import com.prize.lottery.application.query.IAppInfoQueryService;
import com.prize.lottery.application.query.dto.AppVersionQuery;
import com.prize.lottery.infrast.persist.po.AppInfoPo;
import com.prize.lottery.infrast.persist.po.AppVersionPo;
import com.prize.lottery.infrast.persist.vo.AppVersionVo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/adm/app")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class AppManagerController {

    private final IAppInfoQueryService   appInfoQueryService;
    private final IAppInfoCommandService appInfoCommandService;

    @PostMapping("/")
    public void addAppInfo(@Validated AppInfoCreateCmd command) {
        appInfoCommandService.createAppInfo(command);
    }

    @PutMapping("/")
    public void editAppInfo(@Validated AppInfoModifyCmd command) {
        appInfoCommandService.editAppInfo(command);
    }

    @GetMapping("/list")
    public List<AppInfoPo> appInfoList() {
        return appInfoQueryService.getAppInfoList();
    }

    @GetMapping("/{id}")
    public AppInfoPo appInfo(@PathVariable Long id) {
        return appInfoQueryService.getAppInfo(id);
    }

    @GetMapping("/")
    public AppInfoPo appInfo(@NotBlank(message = "应用标识为空") String appNo) {
        return appInfoQueryService.getAppInfo(appNo);
    }

    @PostMapping("/version/")
    public void addAppVersion(@Validated @RequestBody VersionCreateCmd command) {
        appInfoCommandService.createAppVersion(command);
    }

    @PutMapping("/version/")
    public void editAppVersion(@Validated @RequestBody VersionModifyCmd command) {
        appInfoCommandService.editAppVersion(command);
    }

    @PutMapping("/version/online/{id}")
    public void onlineAppVersion(@PathVariable Long id) {
        appInfoCommandService.onlineVersion(id);
    }

    @PutMapping("/version/offline/{id}")
    public void offlineAppVersion(@PathVariable Long id) {
        appInfoCommandService.offlineVersion(id);
    }

    @PutMapping("/version/main/{id}")
    public void issueMainVersion(@PathVariable Long id) {
        appInfoCommandService.issueAppMainVersion(id);
    }

    @GetMapping("/version/{id}")
    public AppVersionPo appVersion(@PathVariable Long id) {
        return appInfoQueryService.getAppVersionDetail(id);
    }

    @GetMapping("/version/list")
    public Page<AppVersionVo> versionList(@Validated AppVersionQuery query) {
        return appInfoQueryService.getAppVersionList(query);
    }
}
