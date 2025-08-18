package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.expert.model.ExpertBalance;
import com.prize.lottery.domain.expert.repository.IExpertBalanceRepository;
import com.prize.lottery.domain.expert.valobj.ExpertIncome;
import com.prize.lottery.infrast.persist.mapper.ExpertAcctMapper;
import com.prize.lottery.infrast.persist.po.ExpertAcctPo;
import com.prize.lottery.infrast.repository.converter.ExpertAccountConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExpertBalanceRepository implements IExpertBalanceRepository {

    private final ExpertAcctMapper       mapper;
    private final ExpertAccountConverter converter;

    @Override
    public void save(ExpertBalance balance) {
        //账户操作
        ExpertAcctPo expertAcct = converter.toAcct(balance.getOperation());
        mapper.saveExpertBalance(expertAcct);
        //账户收益记录
        ExpertIncome income = balance.getExpertIncome();
        if (income != null) {
            mapper.addExpertIncome(converter.toPo(income));
        }
    }

    @Override
    public Optional<ExpertBalance> ofId(Long userId) {
        return Optional.ofNullable(mapper.getExpertAcctByUserId(userId)).map(converter::toBalanceDo);
    }

    @Override
    public Optional<ExpertBalance> ofMaster(String masterId) {
        return Optional.ofNullable(mapper.getExpertAcctByMasterId(masterId)).map(converter::toBalanceDo);
    }
}
