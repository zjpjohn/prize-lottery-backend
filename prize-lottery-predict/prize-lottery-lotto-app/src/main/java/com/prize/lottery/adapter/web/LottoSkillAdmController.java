package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.dto.LotterySkillAdmQuery;
import com.prize.lottery.application.query.service.ILottoSkillQueryService;
import com.prize.lottery.po.lottery.LotterySkillPo;
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
@RequestMapping(value = "/adm/skill")
@Permission(domain = LotteryAuth.MANAGER)
public class LottoSkillAdmController {

    private final ILottoSkillQueryService lottoSkillQueryService;

    @GetMapping("/list")
    public Page<LotterySkillPo> skillList(@Validated LotterySkillAdmQuery query) {
        return lottoSkillQueryService.getLotterySkillList(query.from());
    }

    @GetMapping("/{seq}")
    public LotterySkillPo skillDetail(@PathVariable String seq) {
        return lottoSkillQueryService.getLotterySkillDetail(seq);
    }

}
