package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.expert.model.ExpertAcct;
import com.prize.lottery.domain.expert.model.ExpertBalance;
import com.prize.lottery.domain.expert.valobj.ExpertIncome;
import com.prize.lottery.domain.expert.valobj.ExpertOperation;
import com.prize.lottery.infrast.persist.po.ExpertAcctPo;
import com.prize.lottery.infrast.persist.po.ExpertIncomePo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpertAccountConverter {

    ExpertAcctPo toPo(ExpertAcct account);

    ExpertAcct toDo(ExpertAcctPo account);

    ExpertBalance toBalanceDo(ExpertAcctPo balance);

    ExpertIncomePo toPo(ExpertIncome income);

    ExpertAcctPo toAcct(ExpertOperation operation);

}
