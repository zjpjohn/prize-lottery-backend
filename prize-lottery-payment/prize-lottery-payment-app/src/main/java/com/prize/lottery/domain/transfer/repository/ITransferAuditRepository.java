package com.prize.lottery.domain.transfer.repository;


import com.prize.lottery.domain.transfer.model.aggregate.TransferAuditDo;

public interface ITransferAuditRepository {

    void save(TransferAuditDo audit);

    TransferAuditDo ofNo(String auditNo);

}
