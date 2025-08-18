package com.prize.lottery.application.query;


import com.prize.lottery.infrast.persist.po.TransferRulePo;

import java.util.List;

public interface ITransferRuleQueryService {

    List<TransferRulePo> getTransferRules();

    TransferRulePo getTransferSceneRule(String scene);

    TransferRulePo getTransferSceneRule(Long id);

}
