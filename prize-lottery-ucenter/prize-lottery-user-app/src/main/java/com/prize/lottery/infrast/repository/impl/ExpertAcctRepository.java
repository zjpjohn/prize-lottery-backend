package com.prize.lottery.infrast.repository.impl;

import com.prize.lottery.domain.expert.model.ExpertAcct;
import com.prize.lottery.domain.expert.repository.IExpertAcctRepository;
import com.prize.lottery.infrast.persist.mapper.ExpertAcctMapper;
import com.prize.lottery.infrast.persist.po.ExpertAcctPo;
import com.prize.lottery.infrast.repository.converter.ExpertAccountConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExpertAcctRepository implements IExpertAcctRepository {

    private final ExpertAcctMapper       mapper;
    private final ExpertAccountConverter converter;

    @Override
    public void save(ExpertAcct account) {
        ExpertAcctPo expertAcct = converter.toPo(account);
        mapper.saveExpertAcct(expertAcct);
    }

    @Override
    public ExpertAcct ofUser(Long userId) {
        ExpertAcctPo account = mapper.getExpertAcctByUserId(userId);
        return Optional.ofNullable(account).map(converter::toDo).orElse(null);
    }

    @Override
    public ExpertAcct ofMaster(String masterId) {
        ExpertAcctPo account = mapper.getExpertAcctByMasterId(masterId);
        return Optional.ofNullable(account).map(converter::toDo).orElse(null);
    }

}
