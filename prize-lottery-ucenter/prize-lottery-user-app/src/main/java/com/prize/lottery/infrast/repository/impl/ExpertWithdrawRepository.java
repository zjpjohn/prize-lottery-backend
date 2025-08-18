package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.expert.repository.IExpertWithdrawRepository;
import com.prize.lottery.domain.user.model.UserWithdrawDo;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.mapper.ExpertAcctMapper;
import com.prize.lottery.infrast.persist.po.ExpertWithdrawPo;
import com.prize.lottery.infrast.repository.converter.WithdrawConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpertWithdrawRepository implements IExpertWithdrawRepository {

    private final ExpertAcctMapper  mapper;
    private final WithdrawConverter converter;

    @Override
    public void save(Aggregate<Long, UserWithdrawDo> aggregate) {
        UserWithdrawDo root = aggregate.getRoot();
        if (root.isNew()) {
            ExpertWithdrawPo expertWithdraw = converter.toExpert(root);
            mapper.addExpertWithdraw(expertWithdraw);
            return;
        }
        aggregate.ifChanged().map(converter::toExpert).ifPresent(mapper::editExpertWithdraw);
    }

    @Override
    public Optional<Aggregate<Long, UserWithdrawDo>> ofSeqNo(String seqNo) {
        return Optional.ofNullable(mapper.getWithdrawBySeqNo(seqNo)).map(v -> {
            UserWithdrawDo withdraw = converter.toDo(v);
            withdraw.setScene(TransferScene.USER_EXPERT_TRANS);
            return withdraw;
        }).map(AggregateFactory::create);
    }

}
