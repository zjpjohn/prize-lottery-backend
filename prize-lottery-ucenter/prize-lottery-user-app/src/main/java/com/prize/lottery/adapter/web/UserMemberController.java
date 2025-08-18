package com.prize.lottery.adapter.web;


import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IUserCommandService;
import com.prize.lottery.application.command.dto.MemberOpenCmd;
import com.prize.lottery.application.query.IUserInfoQueryService;
import com.prize.lottery.application.query.dto.MemberLogQuery;
import com.prize.lottery.application.query.dto.UserMemberQuery;
import com.prize.lottery.infrast.persist.po.UserMemberLogPo;
import com.prize.lottery.infrast.persist.vo.UserMemberVo;
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
@RequestMapping("/adm/member")
@Permission(domain = LotteryAuth.MANAGER)
public class UserMemberController {

    private final IUserCommandService   userCommandService;
    private final IUserInfoQueryService userInfoQueryService;

    @PostMapping("/")
    public void openMember(@Validated MemberOpenCmd command) {
        userCommandService.memberOpen(command);
    }

    @GetMapping("/")
    public UserMemberVo userMember(@NotNull(message = "用户标识为空") Long userId) {
        return userInfoQueryService.getUserMemberVo(userId);
    }

    @GetMapping("/list")
    public Page<UserMemberVo> memberList(@Validated UserMemberQuery query) {
        return userInfoQueryService.getUserMemberList(query);
    }

    @GetMapping("/log/list")
    public Page<UserMemberLogPo> memberLogs(@Validated MemberLogQuery query) {
        return userInfoQueryService.getMemberLogList(query);
    }

}
