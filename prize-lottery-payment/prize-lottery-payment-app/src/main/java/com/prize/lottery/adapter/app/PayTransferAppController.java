package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.IPayTransferQueryService;
import com.prize.lottery.application.query.dto.AppTransferQuery;
import com.prize.lottery.application.query.vo.AppTransferRecordVo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
@Permission(domain = LotteryAuth.USER)
public class PayTransferAppController {

    private final IPayTransferQueryService transferQueryService;

    @GetMapping("/list")
    public Page<AppTransferRecordVo> transferRecords(@Validated AppTransferQuery query) {
        return transferQueryService.appTransferList(query);
    }

    @GetMapping("/")
    public AppTransferRecordVo transferRecord(@NotBlank(message = "提现业务号为空") String bizNo) {
        return transferQueryService.appTransInfo(bizNo);
    }

}
