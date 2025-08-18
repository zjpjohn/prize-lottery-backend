package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.IncomeListQuery;
import com.prize.lottery.infrast.persist.po.ExpertAcctPo;
import com.prize.lottery.infrast.persist.po.ExpertIncomePo;

public interface IUserExpertQueryService {

    Page<ExpertIncomePo> getExpertIncomeList(IncomeListQuery query);

    ExpertAcctPo getUserExpertAcct(Long userId);

}
