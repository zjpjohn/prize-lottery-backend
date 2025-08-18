package com.prize.lottery.adapter;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.service.ILotterySkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@RestController
@RequestMapping("/skill")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class LotterySkillController {

    private final ILotterySkillService lotterySkillService;

    @PostMapping("/init")
    public void initFetch() {
        lotterySkillService.initialFetchSkill();
    }

    @PostMapping("/fetch")
    public void fetchSkill() {
        lotterySkillService.fetchSkill();
    }

}
