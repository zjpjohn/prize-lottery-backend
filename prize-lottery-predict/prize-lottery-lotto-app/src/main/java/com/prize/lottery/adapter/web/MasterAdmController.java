package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.dto.FeedAdmQuery;
import com.prize.lottery.application.query.dto.MasterGladQuery;
import com.prize.lottery.application.query.dto.MasterListQuery;
import com.prize.lottery.application.query.service.IMasterQueryService;
import com.prize.lottery.application.vo.MasterInfoDetailVo;
import com.prize.lottery.vo.MasterFeedVo;
import com.prize.lottery.vo.MasterGladVo;
import com.prize.lottery.vo.MasterInfoVo;
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
@RequestMapping("/adm/master")
@Permission(domain = LotteryAuth.MANAGER)
public class MasterAdmController {

    private final IMasterQueryService masterQueryService;

    @GetMapping("/feeds/list")
    public Page<MasterFeedVo> feedsList(@Validated FeedAdmQuery query) {
        return masterQueryService.getMasterFeedList(query);
    }

    @GetMapping("/list")
    public Page<MasterInfoVo> masterList(@Validated MasterListQuery query) {
        return masterQueryService.getMasterInfoList(query);
    }

    @GetMapping("/{masterId}")
    public MasterInfoDetailVo masterDetail(@PathVariable String masterId) {
        return masterQueryService.getAdmMasterInfoVo(masterId);
    }

    @GetMapping("/glad/list")
    public Page<MasterGladVo> masterGladList(@Validated MasterGladQuery query) {
        return masterQueryService.getMasterGladList(query);
    }

}
