package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IActivityCommandService;
import com.prize.lottery.application.command.dto.ActivityCreateCmd;
import com.prize.lottery.application.command.dto.ActivityEditCmd;
import com.prize.lottery.application.query.IActivityQueryService;
import com.prize.lottery.application.query.dto.ActivityDrawQuery;
import com.prize.lottery.application.query.dto.ActivityListQuery;
import com.prize.lottery.application.query.vo.ActivityDrawVo;
import com.prize.lottery.infrast.persist.po.ActivityMemberPo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/activity")
@Permission(domain = LotteryAuth.MANAGER)
public class ActivityAdmController {

    private final IActivityCommandService activityCommandService;
    private final IActivityQueryService   activityQueryService;

    @PostMapping("/")
    public void createActivity(@Validated @RequestBody ActivityCreateCmd command) {
        activityCommandService.createActivity(command);
    }

    @PutMapping("/")
    public void editActivity(@Validated @RequestBody ActivityEditCmd command) {
        activityCommandService.editActivity(command);
    }

    @GetMapping("/list")
    public Page<ActivityMemberPo> activityList(@Validated ActivityListQuery query) {
        return activityQueryService.getActivityList(query);
    }

    @GetMapping("/")
    public ActivityMemberPo activity(@NotNull(message = "活动标识为空") Long id) {
        return activityQueryService.getActivity(id);
    }

    @GetMapping("/draw/list")
    public Page<ActivityDrawVo> activityDrawList(@Validated ActivityDrawQuery query) {
        return activityQueryService.getActivityDrawList(query);
    }

    @GetMapping("/draw/")
    public ActivityDrawVo activityDraw(@NotNull(message = "抽奖标识为空") Long id) {
        return activityQueryService.getActivityDraw(id);
    }

}
