package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.transfer.model.aggregate.TransferRuleDo;
import com.prize.lottery.domain.transfer.model.specs.TransferRuleSpec;
import com.prize.lottery.domain.transfer.repository.ITransferRuleRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.RuleState;
import com.prize.lottery.infrast.persist.mapper.TransferRuleMapper;
import com.prize.lottery.infrast.persist.po.TransferRulePo;
import com.prize.lottery.infrast.props.DefaultRuleProperties;
import com.prize.lottery.infrast.repository.converter.TransferRuleConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
@EnableConfigurationProperties(DefaultRuleProperties.class)
public class TransferRuleRepository implements ITransferRuleRepository {

    private final TransferRuleMapper    mapper;
    private final TransferRuleConverter converter;
    private final DefaultRuleProperties properties;

    @Override
    public void save(Aggregate<Long, TransferRuleDo> aggregate) {
        TransferRuleDo root = aggregate.getRoot();
        if (root.isNew()) {
            TransferRulePo transferRule = converter.toPo(root);
            int            result       = mapper.addTransferRule(transferRule);
            Assert.state(result > 0, ResponseHandler.DATA_SAVE_ERROR);
            return;
        }
        TransferRuleDo changed = aggregate.changed();
        if (changed != null) {
            TransferRulePo transferRule = converter.toPo(changed);
            mapper.editTransferRule(transferRule);
        }
    }

    @Override
    public Aggregate<Long, TransferRuleDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getTransferRuleById(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.TRANSFER_RULE_NONE);
    }

    @Override
    public TransferRuleSpec ofScene(String scene) {
        return Optional.ofNullable(mapper.getTransferSceneRule(scene))
                       .filter(rule -> rule.getState() == RuleState.ISSUED)
                       .map(TransferRuleSpec::from)
                       .orElseGet(properties::toRule);
    }

}
