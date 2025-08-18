package com.prize.lottery.adapter.adm;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IPayTransferCommandService;
import com.prize.lottery.application.command.dto.TransferAuditCmd;
import com.prize.lottery.application.query.IPayTransferQueryService;
import com.prize.lottery.application.query.dto.AdmTransferQuery;
import com.prize.lottery.application.query.vo.AdmTransferRecordVo;
import com.prize.lottery.infrast.persist.po.TransferAuditPo;
import com.prize.lottery.infrast.persist.po.TransferStatisticsPo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/transfer")
@Permission(domain = LotteryAuth.MANAGER)
public class PayTransferAdmController {

    private final IPayTransferQueryService   transferQueryService;
    private final IPayTransferCommandService transferCommandService;

    @GetMapping("/list")
    public Page<AdmTransferRecordVo> transferRecords(@Validated AdmTransferQuery query) {
        return transferQueryService.admTransferList(query);
    }

    @GetMapping("/")
    public AdmTransferRecordVo transferInfo(@NotBlank(message = "提现编号为空") String transNo) {
        return transferQueryService.admTransInfo(transNo);
    }

    @PostMapping("/audit")
    public void auditTransfer(@Validated TransferAuditCmd command) {
        transferCommandService.auditTransfer(command);
    }

    @GetMapping("/audit/list")
    public List<TransferAuditPo> transferAuditList(String transNo) {
        return transferQueryService.transferAuditList(transNo);
    }

    @GetMapping("/metrics")
    public List<TransferStatisticsPo> transferStatistics() {
        return transferQueryService.getTransferStatistics();
    }

}
