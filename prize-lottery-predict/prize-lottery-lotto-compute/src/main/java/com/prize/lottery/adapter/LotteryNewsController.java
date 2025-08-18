package com.prize.lottery.adapter;


import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.service.ILotteryNewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ApiBody
@RestController
@RequestMapping("/news")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class LotteryNewsController {

    private final ILotteryNewsService lotteryNewsService;

    @PostMapping("/init")
    public void initFetch() {
        lotteryNewsService.initialFetchNews();
    }

    @PostMapping("/fetch")
    public void fetchNews() {
        lotteryNewsService.fetchNews();
    }
}
