package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.dto.Num3LayerQuery;
import com.prize.lottery.application.query.dto.Num3WarnQuery;
import com.prize.lottery.application.query.service.INum3WarnQueryService;
import com.prize.lottery.po.lottery.Num3ComWarningPo;
import com.prize.lottery.po.lottery.Num3LayerFilterPo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Validated
@ApiBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/warning")
@Permission(domain = LotteryAuth.MANAGER)
public class Num3WarningController {

    private final INum3WarnQueryService num3WarnQueryService;

    @GetMapping("/list")
    public Page<Num3ComWarningPo> warningList(Num3WarnQuery query) {
        return num3WarnQueryService.getComWarningList(query);
    }

    @GetMapping("/")
    public Num3ComWarningPo comWarning(@NotNull(message = "唯一标识为空") Long id) {
        return num3WarnQueryService.getComWarning(id);
    }

    @GetMapping("/layer/list")
    public Page<Num3LayerFilterPo> layerList(@Validated Num3LayerQuery query) {
        return num3WarnQueryService.getNum3LayerList(query);
    }

    @GetMapping("/layer/")
    public Num3LayerFilterPo num3Layer(@NotNull(message = "唯一标识为空") Long id) {
        return num3WarnQueryService.getNum3Layer(id);
    }

}
