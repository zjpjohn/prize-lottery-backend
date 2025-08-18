package com.prize.lottery.adapter.web;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.dto.AvatarCreateCmd;
import com.prize.lottery.application.command.service.IAvatarCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/avatar")
@Permission(domain = LotteryAuth.MANAGER)
public class AvatarAdmController {

    private final IAvatarCommandService avatarCommandService;

    @PostMapping("/")
    public void addAvatar(@Validated AvatarCreateCmd avatar) {
        avatarCommandService.createAvatar(avatar);
    }

    @DeleteMapping("/{id}")
    public void removeAvatar(@PathVariable Long id) {
        avatarCommandService.removeAvatar(id);
    }
}
