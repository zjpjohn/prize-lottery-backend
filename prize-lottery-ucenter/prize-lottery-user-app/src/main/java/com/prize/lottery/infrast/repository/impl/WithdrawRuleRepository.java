package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.withdraw.model.WithdrawRuleDo;
import com.prize.lottery.domain.withdraw.repository.IWithdrawRuleRepository;
import com.prize.lottery.domain.withdraw.specs.WithdrawRuleSpec;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.WithdrawRuleState;
import com.prize.lottery.infrast.persist.mapper.WithdrawRuleMapper;
import com.prize.lottery.infrast.persist.po.WithdrawRulePo;
import com.prize.lottery.infrast.repository.converter.WithdrawConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WithdrawRuleRepository implements IWithdrawRuleRepository {

    private final WithdrawRuleMapper mapper;
    private final WithdrawConverter  converter;

    @Override
    public void save(Aggregate<Long, WithdrawRuleDo> aggregate) {
        WithdrawRuleDo root = aggregate.getRoot();
        if (root.isNew()) {
            WithdrawRulePo withdrawRule = converter.toPo(root);
            mapper.addWithdrawRule(withdrawRule);
            return;
        }
        aggregate.ifChanged().ifPresent(changed -> {
            WithdrawRulePo withdrawRule = converter.toPo(changed);
            withdrawRule.setScene(root.getScene());
            //当规则预发布或启用时，先撤销已经预发布或已启用的规则
            WithdrawRuleState state = withdrawRule.getState();
            if (state == WithdrawRuleState.PRE_ISSUED || state == WithdrawRuleState.ISSUED) {
                mapper.autoRevokeWithdrawRule(withdrawRule);
            }
            mapper.editWithdrawRule(withdrawRule);
        });
    }

    @Override
    public Aggregate<Long, WithdrawRuleDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getWithdrawRuleById(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.WITHDRAW_RULE_NONE);
    }

    @Override
    public Optional<WithdrawRuleSpec> ofScene(String scene) {
        return Optional.ofNullable(mapper.getSceneUsingRule(scene))
                       .filter(rule -> rule.getState() == WithdrawRuleState.ISSUED)
                       .map(rule -> new WithdrawRuleSpec(rule.getThrottle(), rule.getMaximum(), rule.getInterval()));
    }

}
