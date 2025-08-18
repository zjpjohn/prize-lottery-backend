package com.prize.lottery.adapter.web;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IExpertCommandService;
import com.prize.lottery.application.query.IUserExpertQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/expert")
@Permission(domain = LotteryAuth.MANAGER)
public class UserExpertAdmController {

    private final IExpertCommandService   expertCommandService;
    private final IUserExpertQueryService expertQueryService;

}
