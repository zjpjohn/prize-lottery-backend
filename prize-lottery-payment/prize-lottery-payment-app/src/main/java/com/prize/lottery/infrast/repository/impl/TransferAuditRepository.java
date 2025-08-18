package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.transfer.model.aggregate.TransferAuditDo;
import com.prize.lottery.domain.transfer.repository.ITransferAuditRepository;
import com.prize.lottery.infrast.persist.mapper.TransferRecordMapper;
import com.prize.lottery.infrast.persist.po.TransferAuditPo;
import com.prize.lottery.infrast.repository.converter.PayTransferConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TransferAuditRepository implements ITransferAuditRepository {

    private final TransferRecordMapper mapper;
    private final PayTransferConverter converter;

    @Override
    public void save(TransferAuditDo audit) {
        TransferAuditPo transferAudit = converter.toPo(audit);
        mapper.addTransferAudit(transferAudit);
    }

    @Override
    public TransferAuditDo ofNo(String auditNo) {
        return Optional.ofNullable(mapper.getAuditInfo(auditNo)).map(converter::toDo).orElse(null);
    }

}
