package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.dto.LotteryNewsAdmQuery;
import com.prize.lottery.application.query.service.ILottoNewsQueryService;
import com.prize.lottery.po.lottery.LotteryNewsPo;
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
@RequestMapping(value = "/adm/news")
@Permission(domain = LotteryAuth.MANAGER)
public class LottoNewsAdmController {

    private final ILottoNewsQueryService newsQueryService;

    @GetMapping("/list")
    public Page<LotteryNewsPo> newsList(@Validated LotteryNewsAdmQuery query) {
        return newsQueryService.getLotteryNewsList(query.from());
    }

    @GetMapping("/{seq}")
    public LotteryNewsPo newsDetail(@PathVariable String seq) {
        return newsQueryService.getLotteryNewsDetail(seq);
    }
}
