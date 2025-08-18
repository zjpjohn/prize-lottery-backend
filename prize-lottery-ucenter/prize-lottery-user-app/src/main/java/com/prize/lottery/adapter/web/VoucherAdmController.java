package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IVoucherCommandService;
import com.prize.lottery.application.command.dto.VoucherCreateCmd;
import com.prize.lottery.application.command.dto.VoucherEditCmd;
import com.prize.lottery.application.query.IVoucherQueryService;
import com.prize.lottery.application.query.dto.AdmVoucherLogQuery;
import com.prize.lottery.application.query.dto.AdmVoucherQuery;
import com.prize.lottery.infrast.persist.po.UserVoucherLogPo;
import com.prize.lottery.infrast.persist.po.VoucherInfoPo;
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
@RequestMapping("/adm/voucher")
@Permission(domain = LotteryAuth.MANAGER)
public class VoucherAdmController {

    private final IVoucherQueryService   voucherQueryService;
    private final IVoucherCommandService voucherCommandService;

    @PostMapping("/")
    public void createVoucher(@Validated VoucherCreateCmd command) {
        voucherCommandService.createVoucher(command);
    }

    @PutMapping("/")
    public void editVoucher(@Validated VoucherEditCmd command) {
        voucherCommandService.editVoucher(command);
    }

    @GetMapping("/")
    public VoucherInfoPo voucherInfo(@NotNull(message = "唯一标识为空") String seqNo) {
        return voucherQueryService.getVoucherInfo(seqNo);
    }

    @GetMapping("/list")
    public Page<VoucherInfoPo> voucherList(@Validated AdmVoucherQuery query) {
        return voucherQueryService.getVoucherList(query);
    }

    @GetMapping("/log/list")
    public Page<UserVoucherLogPo> voucherLogList(@Validated AdmVoucherLogQuery query) {
        return voucherQueryService.getVoucherLogList(query);
    }

}
