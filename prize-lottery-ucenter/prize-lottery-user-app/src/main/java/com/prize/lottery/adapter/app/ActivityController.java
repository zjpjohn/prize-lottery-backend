package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IActivityCommandService;
import com.prize.lottery.application.command.vo.ActivityChanceVo;
import com.prize.lottery.application.command.vo.ActivityJoinResult;
import com.prize.lottery.application.command.vo.DrawMemberResult;
import com.prize.lottery.application.query.IActivityQueryService;
import com.prize.lottery.application.query.dto.UserDrawQuery;
import com.prize.lottery.infrast.persist.vo.UserDrawResultVo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/activity")
@Permission(domain = LotteryAuth.USER)
public class ActivityController {

    private final IActivityQueryService   activityQueryService;
    private final IActivityCommandService activityCommandService;

    @PostMapping("/join")
    public ActivityJoinResult joinActivity(@NotNull(message = "用户标识为空") Long userId) {
        return activityCommandService.joinActivity(userId);
    }

    @PutMapping("/draw")
    public DrawMemberResult drawActivity(@NotNull(message = "抽奖标识为空") Long drawId,
                                         @NotNull(message = "用户表示为空") Long userId) {
        return activityCommandService.drawActivity(drawId, userId);
    }

    @GetMapping("/draw/chances")
    public List<ActivityChanceVo> drawChances(@NotNull(message = "抽奖标识为空") Long drawId) {
        return activityQueryService.getDrawChances(drawId);
    }

    @GetMapping("/draw/list")
    public Page<UserDrawResultVo> userDrawList(@Validated UserDrawQuery query) {
        return activityQueryService.getUserDrawResults(query);
    }

}
