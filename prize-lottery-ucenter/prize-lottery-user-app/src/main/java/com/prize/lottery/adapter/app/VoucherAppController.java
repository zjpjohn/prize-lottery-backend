package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IVoucherCommandService;
import com.prize.lottery.application.command.dto.DrawBatchVoucherCmd;
import com.prize.lottery.application.command.dto.DrawVoucherCmd;
import com.prize.lottery.application.command.vo.DrawVoucherVo;
import com.prize.lottery.application.query.IVoucherQueryService;
import com.prize.lottery.application.query.dto.AppVoucherLogQuery;
import com.prize.lottery.application.query.vo.AppVoucherInfoVo;
import com.prize.lottery.application.query.vo.VoucherItemVo;
import com.prize.lottery.infrast.persist.po.UserVoucherLogPo;
import com.prize.lottery.infrast.persist.po.UserVoucherPo;
import com.prize.lottery.infrast.persist.vo.UserDrawVo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/voucher")
@Permission(domain = LotteryAuth.USER)
public class VoucherAppController {

    private final IVoucherCommandService voucherCommandService;
    private final IVoucherQueryService   voucherQueryService;

    @GetMapping("/")
    public UserVoucherPo userVoucher(@NotNull(message = "用户表示为空") Long userId) {
        return voucherQueryService.getUserVoucher(userId);
    }

    @PostMapping("/draw")
    public DrawVoucherVo drawVoucher(@Validated DrawVoucherCmd command) {
        return voucherCommandService.drawVoucher(command);
    }

    @PostMapping("/draw/batch")
    public List<DrawVoucherVo> drawBatch(@Validated @RequestBody DrawBatchVoucherCmd command) {
        return voucherCommandService.drawBatchVoucher(command);
    }

    @GetMapping("/drawable/list")
    public List<VoucherItemVo> canDrawList(@NotNull(message = "用户标识为空") Long userId) {
        return voucherQueryService.canDrawVoucherList(userId);
    }

    @GetMapping("/list")
    public List<AppVoucherInfoVo> voucherList(@NotNull(message = "用户标识为空") Long userId) {
        return voucherQueryService.appVoucherList(userId);
    }

    @GetMapping("/record/list")
    public Page<UserVoucherLogPo> userVouchers(@Validated AppVoucherLogQuery query) {
        return voucherQueryService.getUserVoucherLogList(query);
    }

    @GetMapping("/latest")
    public List<UserDrawVo> userDraws() {
        return voucherQueryService.getLatestUserDraw();
    }

}
