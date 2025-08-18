package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IUserCommandService;
import com.prize.lottery.application.command.vo.UserSignVo;
import com.prize.lottery.application.query.IUserInfoQueryService;
import com.prize.lottery.application.query.dto.SignLogQuery;
import com.prize.lottery.infrast.persist.po.UserSignLogPo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/sign")
@Permission(domain = LotteryAuth.USER)
public class UserSignController {

    private final IUserCommandService   userCommandService;
    private final IUserInfoQueryService userQueryService;

    @PostMapping("/")
    public UserSignVo userSign(@NotNull(message = "用户标识为空") Long userId) {
        return userCommandService.userSign(userId);
    }

    @GetMapping("/")
    public UserSignVo signInfo(@NotNull(message = "用户标识为空") Long userId) {
        return userCommandService.userSignInfo(userId);
    }

    @GetMapping("/logs")
    public Page<UserSignLogPo> signLogs(@Validated SignLogQuery query) {
        return userQueryService.getUserSignLogs(query);
    }
}
