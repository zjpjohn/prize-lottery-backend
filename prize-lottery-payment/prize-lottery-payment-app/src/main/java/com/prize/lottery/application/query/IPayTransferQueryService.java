package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AdmTransferQuery;
import com.prize.lottery.application.query.dto.AppTransferQuery;
import com.prize.lottery.application.query.vo.AdmTransferRecordVo;
import com.prize.lottery.application.query.vo.AppTransferRecordVo;
import com.prize.lottery.infrast.persist.po.TransferAuditPo;
import com.prize.lottery.infrast.persist.po.TransferStatisticsPo;

import java.util.List;

public interface IPayTransferQueryService {

    Page<AppTransferRecordVo> appTransferList(AppTransferQuery query);

    Page<AdmTransferRecordVo> admTransferList(AdmTransferQuery query);

    List<TransferAuditPo> transferAuditList(String transNo);

    AppTransferRecordVo appTransInfo(String bizNo);

    AdmTransferRecordVo admTransInfo(String transNo);

    List<TransferStatisticsPo> getTransferStatistics();

}
