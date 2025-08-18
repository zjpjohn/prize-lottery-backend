package com.prize.lottery.application.query.impl;

import com.prize.lottery.application.query.ITransferRuleQueryService;
import com.prize.lottery.infrast.persist.mapper.TransferRuleMapper;
import com.prize.lottery.infrast.persist.po.TransferRulePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferRuleQueryService implements ITransferRuleQueryService {

    private final TransferRuleMapper transferRuleMapper;

    @Override
    public List<TransferRulePo> getTransferRules() {
        return transferRuleMapper.getAllTransferRules();
    }

    @Override
    public TransferRulePo getTransferSceneRule(String scene) {
        return transferRuleMapper.getTransferSceneRule(scene);
    }

    @Override
    public TransferRulePo getTransferSceneRule(Long id) {
        return transferRuleMapper.getTransferRuleById(id);
    }

}
