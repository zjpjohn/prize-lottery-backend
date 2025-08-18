package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.user.model.UserWithdrawDo;
import com.prize.lottery.domain.user.repository.IUserWithdrawRepository;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.mapper.UserWithdrawMapper;
import com.prize.lottery.infrast.persist.po.UserWithdrawPo;
import com.prize.lottery.infrast.repository.converter.WithdrawConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserWithdrawRepository implements IUserWithdrawRepository {

    private final UserWithdrawMapper mapper;
    private final WithdrawConverter  converter;

    @Override
    public void save(Aggregate<Long, UserWithdrawDo> aggregate) {
        UserWithdrawDo root = aggregate.getRoot();
        if (root.isNew()) {
            UserWithdrawPo withdraw = converter.toUser(root);
            mapper.addUserWithdraw(withdraw);
            return;
        }
        aggregate.ifChanged().map(converter::toUser).ifPresent(mapper::editUserWithdraw);
    }

    @Override
    public Optional<Aggregate<Long, UserWithdrawDo>> ofSeqNo(String seqNo) {
        return Optional.ofNullable(mapper.getWithdrawBySeqNo(seqNo)).map(v -> {
            UserWithdrawDo withdraw = converter.toDo(v);
            withdraw.setScene(TransferScene.USER_REWARD_TRANS);
            return withdraw;
        }).map(AggregateFactory::create);
    }

}
