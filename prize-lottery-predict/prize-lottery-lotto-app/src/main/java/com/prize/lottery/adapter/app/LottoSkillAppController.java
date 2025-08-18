package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.service.ILottoSkillCommandService;
import com.prize.lottery.application.query.dto.LotterySkillAppQuery;
import com.prize.lottery.application.query.service.ILottoSkillQueryService;
import com.prize.lottery.po.lottery.LotterySkillPo;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping(value = "/app/skill")
public class LottoSkillAppController {

    private final ILottoSkillQueryService   lottoSkillQueryService;
    private final ILottoSkillCommandService lottoSkillCommandService;

    @GetMapping("/{seq}")
    @Permission(domain = LotteryAuth.USER)
    public LotterySkillPo skillDetail(@PathVariable String seq, @NotNull(message = "用户标识为空") Long userId) {
        return lottoSkillCommandService.browseSkill(seq, userId);
    }

    @GetMapping("/list")
    public Page<LotterySkillPo> skillList(@Validated LotterySkillAppQuery query) {
        return lottoSkillQueryService.getLotterySkillList(query.from());
    }

}
