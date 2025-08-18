package com.prize.lottery.infrast.persist.mapper;

import com.prize.lottery.infrast.persist.po.TransferRulePo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRuleMapper {

    int addTransferRule(TransferRulePo rule);

    int editTransferRule(TransferRulePo rule);

    TransferRulePo getTransferRuleById(Long id);

    TransferRulePo getTransferSceneRule(String scene);

    List<TransferRulePo> getAllTransferRules();

}
