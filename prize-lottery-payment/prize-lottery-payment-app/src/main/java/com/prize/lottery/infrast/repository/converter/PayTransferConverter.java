package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.transfer.model.aggregate.PayTransferBatchDo;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferOneDo;
import com.prize.lottery.domain.transfer.model.aggregate.TransferAuditDo;
import com.prize.lottery.domain.transfer.model.entity.TransferRecord;
import com.prize.lottery.infrast.persist.po.TransferAuditPo;
import com.prize.lottery.infrast.persist.po.TransferBatchPo;
import com.prize.lottery.infrast.persist.po.TransferRecordPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayTransferConverter {

    TransferRecordPo toPo(TransferRecord record);

    PayTransferOneDo toDo(TransferRecordPo record);

    TransferRecord toRecord(TransferRecordPo record);

    TransferBatchPo toPo(PayTransferBatchDo transferBatch);

    PayTransferBatchDo toDo(TransferBatchPo transferBatch);

    TransferAuditPo toPo(TransferAuditDo transferAudit);

    TransferAuditDo toDo(TransferAuditPo transferAudit);

}
