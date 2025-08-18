package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.withdraw.model.WithdrawLevelDo;
import com.prize.lottery.domain.withdraw.repository.IWithdrawLevelRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.WithLevelSate;
import com.prize.lottery.infrast.persist.mapper.WithdrawRuleMapper;
import com.prize.lottery.infrast.persist.po.WithdrawLevelPo;
import com.prize.lottery.infrast.repository.converter.WithdrawConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawLevelRepository implements IWithdrawLevelRepository {

    private final WithdrawRuleMapper mapper;
    private final WithdrawConverter  converter;

    @Override
    public void save(Aggregate<Long, WithdrawLevelDo> aggregate) {
        WithdrawLevelDo root = aggregate.getRoot();
        if (root.isNew()) {
            WithdrawLevelPo withdrawLevel = converter.toPo(root);
            mapper.addWithdrawLevel(withdrawLevel);
            return;
        }
        WithdrawLevelDo withdrawLevel = aggregate.changed();
        if (withdrawLevel != null) {
            if (withdrawLevel.getState() == WithLevelSate.USING) {
                //启用新的提现等级，撤下旧的正在使用的提现等级
                mapper.revokeSceneLevel(root.getScene().getScene());
            }
            mapper.editWithdrawLevel(converter.toPo(withdrawLevel));
        }
    }

    @Override
    public Aggregate<Long, WithdrawLevelDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getWithdrawLevel(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.WITHDRAW_LEVEL_NONE);
    }
}
