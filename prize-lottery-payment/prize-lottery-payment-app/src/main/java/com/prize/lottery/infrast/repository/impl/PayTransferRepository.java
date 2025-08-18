package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferBatchDo;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferOneDo;
import com.prize.lottery.domain.transfer.model.entity.TransferRecord;
import com.prize.lottery.domain.transfer.repository.IPayTransferRepository;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.infrast.persist.mapper.TransferRecordMapper;
import com.prize.lottery.infrast.persist.po.TransferBatchPo;
import com.prize.lottery.infrast.persist.po.TransferRecordPo;
import com.prize.lottery.infrast.repository.converter.PayTransferConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PayTransferRepository implements IPayTransferRepository {

    private final TransferRecordMapper mapper;
    private final PayTransferConverter converter;

    @Override
    public void saveOne(Aggregate<Long, PayTransferOneDo> aggregate) {
        PayTransferOneDo root = aggregate.getRoot();
        if (root.isNew()) {
            TransferRecordPo record = converter.toPo(root);
            mapper.addTransferRecords(Collections.singletonList(record));
            return;
        }
        aggregate.ifChanged()
                 .map(converter::toPo)
                 .map(Collections::singletonList)
                 .ifPresent(mapper::editTransferRecords);
    }

    @Override
    public Aggregate<Long, PayTransferOneDo> ofOne(String transNo) {
        return Optional.ofNullable(mapper.getTransferRecord(transNo))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElse(null);
    }

    @Override
    public void saveBatch(Aggregate<Long, PayTransferBatchDo> aggregate) {
        PayTransferBatchDo root = aggregate.getRoot();
        if (root.isNew()) {
            TransferBatchPo transferBatch = converter.toPo(root);
            mapper.addTransferBatch(transferBatch);
            TransferRecordPo transferRecord = converter.toPo(root.getRecord());
            mapper.addTransferRecords(Collections.singletonList(transferRecord));
            return;
        }
        PayTransferBatchDo transferBatchDo = aggregate.changed();
        if (transferBatchDo != null) {
            TransferBatchPo transferBatch = converter.toPo(transferBatchDo);
            mapper.editTransferBatch(transferBatch);
            TransferRecord batchDoRecord = transferBatchDo.getRecord();
            if (batchDoRecord == null) {
                return;
            }
            TransferRecordPo transferRecord = converter.toPo(batchDoRecord);
            mapper.editTransferRecords(Collections.singletonList(transferRecord));
        }
    }

    @Override
    public Aggregate<Long, PayTransferBatchDo> batchOfBatchNo(String batchNo) {
        TransferBatchPo transferBatchPo = mapper.getTransferBatch(batchNo);
        if (transferBatchPo == null) {
            return null;
        }
        PayTransferBatchDo     transferBatch  = converter.toDo(transferBatchPo);
        List<TransferRecordPo> records        = mapper.getTransferRecordsByBatchNo(transferBatch.getBatchNo());
        TransferRecord         transferRecord = converter.toRecord(records.get(0));
        transferBatch.setRecord(transferRecord);
        return AggregateFactory.create(transferBatch);
    }

    @Override
    public Aggregate<Long, PayTransferBatchDo> batchOfTransNo(String transNo) {
        TransferRecordPo transferRecordPo = mapper.getTransferRecord(transNo);
        if (transferRecordPo == null || !StringUtils.hasText(transferRecordPo.getBatchNo())) {
            return null;
        }
        TransferBatchPo    transferBatchPo = mapper.getTransferBatch(transferRecordPo.getBatchNo());
        PayTransferBatchDo transferBatch   = converter.toDo(transferBatchPo);
        TransferRecord     transferRecord  = converter.toRecord(transferRecordPo);
        transferBatch.setRecord(transferRecord);
        return AggregateFactory.create(transferBatch);
    }

    @Override
    public boolean isAuditing(String transNo) {
        return Optional.ofNullable(mapper.getTransferRecord(transNo))
                       .map(record -> record.getAudit() == AuditState.PROCESSING)
                       .orElse(false);
    }

}
