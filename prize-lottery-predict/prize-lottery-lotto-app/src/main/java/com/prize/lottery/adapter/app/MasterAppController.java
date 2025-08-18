package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.dto.BatchSubscribeCmd;
import com.prize.lottery.application.command.dto.MasterEnableCmd;
import com.prize.lottery.application.command.dto.SubscribeMasterCmd;
import com.prize.lottery.application.command.service.IMasterCommandService;
import com.prize.lottery.application.query.dto.FeedAppQuery;
import com.prize.lottery.application.query.dto.FocusMasterQuery;
import com.prize.lottery.application.query.service.IMasterQueryService;
import com.prize.lottery.application.vo.MasterInfoDetailVo;
import com.prize.lottery.vo.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@ApiBody
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/app/master")
public class MasterAppController {

    private final IMasterQueryService   masterQueryService;
    private final IMasterCommandService masterCommandService;

    @GetMapping("/feeds/list")
    public List<MasterFeedVo> feedsList(@Validated FeedAppQuery query) {
        return masterQueryService.getAppMasterFeeds(query);
    }

    @GetMapping("/mine")
    @Permission(domain = LotteryAuth.USER)
    public MasterInfoDetailVo mineMaster(@NotNull(message = "用户标识为空") Long userId) {
        return masterQueryService.getMineMasterDetail(userId);
    }

    @PostMapping("/enable")
    @Permission(domain = LotteryAuth.USER)
    public void enableLotteryChannel(@Validated MasterEnableCmd command) {
        masterCommandService.enableMasterLotto(command);
    }

    @PostMapping("/follow")
    @Permission(domain = LotteryAuth.USER)
    public void subscribe(@Validated SubscribeMasterCmd command) {
        masterCommandService.subscribeMaster(command);
    }

    @PostMapping("/follow/batch")
    @Permission(domain = LotteryAuth.USER)
    public void batchSubscribe(@RequestBody @Validated BatchSubscribeCmd command) {
        masterCommandService.batchSubscribeMasters(command);
    }

    @PutMapping("/follow/trace")
    @Permission(domain = LotteryAuth.USER)
    public void traceMaster(@Validated SubscribeMasterCmd command) {
        masterCommandService.traceSubscribeMaster(command);
    }

    @PutMapping("/follow/special")
    @Permission(domain = LotteryAuth.USER)
    public void specialOrCancelMaster(@Validated SubscribeMasterCmd command) {
        masterCommandService.specialOrCancelMaster(command);
    }

    @DeleteMapping("/follow")
    @Permission(domain = LotteryAuth.USER)
    public void unsubscribe(@Validated SubscribeMasterCmd command) {
        masterCommandService.unsubscribeMaster(command);
    }

    @GetMapping("/follow/list")
    @Permission(domain = LotteryAuth.USER)
    public Page<MasterSubscribeVo> followMasters(@Validated FocusMasterQuery query) {
        return masterQueryService.getUserSubscribeMasters(query);
    }

    @PostMapping("/focus")
    @Permission(domain = LotteryAuth.USER)
    public void focusMaster(@NotNull(message = "用户标识为空") Long userId,
                            @NotBlank(message = "专家标识为空") String masterId) {
        masterCommandService.focusMaster(userId, masterId);
    }

    @DeleteMapping("/focus")
    @Permission(domain = LotteryAuth.USER)
    public void unFocusMaster(@NotNull(message = "用户标识为空") Long userId,
                              @NotBlank(message = "专家标识为空") String masterId) {
        masterCommandService.unFocusMaster(userId, masterId);
    }

    @GetMapping("/focus/list")
    @Permission(domain = LotteryAuth.USER)
    public Page<MasterFocusVo> userFocusList(@Validated FocusMasterQuery query) {
        return masterQueryService.getUserFocusMasters(query);
    }

    @DeleteMapping("/battle/{id}")
    @Permission(domain = LotteryAuth.USER)
    public void removeBattle(@PathVariable Long id) {
        masterCommandService.removeMasterBattle(id);
    }

    @GetMapping("/follow/recommend")
    public List<RecommendMasterVo> recommendMasters() {
        return masterQueryService.getRecommendMasters();
    }

    @GetMapping("/glad/list")
    public List<MasterGladVo> masterGlads() {
        return masterQueryService.getAppGladList();
    }

}
