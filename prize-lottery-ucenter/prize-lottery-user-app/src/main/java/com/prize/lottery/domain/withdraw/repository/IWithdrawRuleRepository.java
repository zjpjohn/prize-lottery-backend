package com.prize.lottery.domain.withdraw.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.withdraw.model.WithdrawRuleDo;
import com.prize.lottery.domain.withdraw.specs.WithdrawRuleSpec;

import java.util.Optional;

public interface IWithdrawRuleRepository {

    void save(Aggregate<Long, WithdrawRuleDo> aggregate);

    Aggregate<Long, WithdrawRuleDo> ofId(Long id);

    Optional<WithdrawRuleSpec> ofScene(String scene);

}
