package com.prize.lottery.adapter;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.service.IMasterInfoService;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/master")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class MasterInfoController {

    private final IMasterInfoService masterInfoService;

    @PostMapping("/feed/extract")
    public void feedExtract() {
        masterInfoService.extractMasterFeeds();
    }

    @DeleteMapping("/feed")
    public void removeFeed(@NotNull(message = "彩种类型为空") LotteryEnum type, @Range(
            min = 0, max = 1, message = "命中率在0~1之间") @NotNull(message = "命中率为空") Double rate) {
        masterInfoService.removeMasterFeed(type, rate);
    }

    @PostMapping("/glad/extract")
    public void gladExtract() {
        masterInfoService.extractGlads();
    }

    @DeleteMapping("/glad")
    public void removeGlads(@Enumerable(ranges = {"7", "14", "21", "30", "60", "90"}, message = "指定天数错误")
                            @NotNull(message = "指定天数为空") Integer day) {
        masterInfoService.removeGlads(day);
    }

    @PostMapping("/avatar")
    public void loadAvatars() {
        masterInfoService.importAvatars();
    }

    @PutMapping("/avatar")
    public void resetMasterAvatar() {
        masterInfoService.resetMasterAvatars();
    }

    @DeleteMapping("/avatar/{id}")
    public void removeAvatar(@PathVariable Long id) {
        masterInfoService.removeAvatar(id);
    }

    @PostMapping("/load")
    public void loadMasters(@NotNull(message = "彩种类型为空") LotteryEnum type) {
        masterInfoService.importMasters(type);
    }

    @PutMapping("/name/reset")
    public void resetMasterName() {
        masterInfoService.resetMasterNames();
    }

    @PutMapping("/glad/extract")
    public void extractGlad() {
        masterInfoService.extractGlads();
    }

}
