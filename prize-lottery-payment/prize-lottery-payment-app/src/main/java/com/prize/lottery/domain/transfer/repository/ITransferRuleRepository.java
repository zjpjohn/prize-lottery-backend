package com.prize.lottery.domain.transfer.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.transfer.model.aggregate.TransferRuleDo;
import com.prize.lottery.domain.transfer.model.specs.TransferRuleSpec;

public interface ITransferRuleRepository {

    void save(Aggregate<Long, TransferRuleDo> aggregate);

    Aggregate<Long, TransferRuleDo> ofId(Long id);

    TransferRuleSpec ofScene(String scene);

}
