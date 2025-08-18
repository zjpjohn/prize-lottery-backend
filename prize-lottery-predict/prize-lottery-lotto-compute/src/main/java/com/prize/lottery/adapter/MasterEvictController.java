package com.prize.lottery.adapter;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.service.IMasterEvictService;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/master/evict")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class MasterEvictController {

    private final IMasterEvictService masterEvictService;

    @PostMapping("/extract")
    public void extractEvicts(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        masterEvictService.extractEvicts(type);
    }

    @DeleteMapping("/forecast")
    public void clearForecast(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        masterEvictService.clearForecasts(type);
    }

    @DeleteMapping("/record")
    public void clearMaster(@NotNull(message = "彩票类型为空") LotteryEnum type) {
        masterEvictService.clearMasters(type);
    }
}
