package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.IUserExpertQueryService;
import com.prize.lottery.application.query.dto.IncomeListQuery;
import com.prize.lottery.infrast.persist.mapper.ExpertAcctMapper;
import com.prize.lottery.infrast.persist.po.ExpertAcctPo;
import com.prize.lottery.infrast.persist.po.ExpertIncomePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserExpertQueryService implements IUserExpertQueryService {

    private final ExpertAcctMapper expertAcctMapper;

    @Override
    public Page<ExpertIncomePo> getExpertIncomeList(IncomeListQuery query) {
        return query.from().count(expertAcctMapper::countExpertIncomes).query(expertAcctMapper::getExpertIncomeList);

    }

    @Override
    public ExpertAcctPo getUserExpertAcct(Long userId) {
        return expertAcctMapper.getExpertAcctByUserId(userId);
    }
}
