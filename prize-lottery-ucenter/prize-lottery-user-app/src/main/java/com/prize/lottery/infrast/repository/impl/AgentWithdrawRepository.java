package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.agent.repository.IAgentWithdrawRepository;
import com.prize.lottery.domain.user.model.UserWithdrawDo;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.mapper.UserInviteMapper;
import com.prize.lottery.infrast.persist.po.AgentWithdrawPo;
import com.prize.lottery.infrast.repository.converter.WithdrawConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgentWithdrawRepository implements IAgentWithdrawRepository {

    private final UserInviteMapper  mapper;
    private final WithdrawConverter converter;

    @Override
    public void save(Aggregate<Long, UserWithdrawDo> aggregate) {
        UserWithdrawDo root = aggregate.getRoot();
        if (root.isNew()) {
            AgentWithdrawPo agentWithdraw = converter.toAgent(root);
            mapper.addAgentWithdraw(agentWithdraw);
            return;
        }
        aggregate.ifChanged().map(converter::toAgent).ifPresent(mapper::editAgentWithdraw);
    }

    @Override
    public Optional<Aggregate<Long, UserWithdrawDo>> ofSeqNo(String seqNo) {
        return Optional.ofNullable(mapper.getWithdrawBySeqNo(seqNo)).map(v -> {
            UserWithdrawDo withdraw = converter.toDo(v);
            withdraw.setScene(TransferScene.USER_AGENT_TRANS);
            return withdraw;
        }).map(AggregateFactory::create);
    }

}
