package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.service.INewsCommandService;
import com.prize.lottery.application.query.dto.LotteryNewsAppQuery;
import com.prize.lottery.application.query.service.ILottoNewsQueryService;
import com.prize.lottery.po.lottery.LotteryNewsPo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Validated
@ApiBody
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/app/news")
public class LottoNewsAppController {

    private final ILottoNewsQueryService newsQueryService;
    private final INewsCommandService    newsCommandService;

    @GetMapping("/list")
    public Page<LotteryNewsPo> newsList(@Validated LotteryNewsAppQuery query) {
        return newsQueryService.getLotteryNewsList(query.from());
    }

    @GetMapping("/{seq}")
    @Permission(domain = LotteryAuth.USER)
    public LotteryNewsPo newsDetail(@PathVariable String seq, @NotNull(message = "用户标识为空") Long userId) {
        return newsCommandService.browseNews(seq, userId);
    }
}
