package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.TransferRuleAssembler;
import com.prize.lottery.application.command.ITransferRuleCommandService;
import com.prize.lottery.application.command.dto.TransRuleCreateCmd;
import com.prize.lottery.application.command.dto.TransRuleModifyCmd;
import com.prize.lottery.domain.transfer.model.aggregate.TransferRuleDo;
import com.prize.lottery.domain.transfer.repository.ITransferRuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferRuleCommandService implements ITransferRuleCommandService {

    private final TransferRuleAssembler   ruleAssembler;
    private final ITransferRuleRepository ruleRepository;

    @Override
    @Transactional
    public void createRule(TransRuleCreateCmd command) {
        TransferRuleDo transferRule = new TransferRuleDo(command.validate(), ruleAssembler::toDo);
        AggregateFactory.create(transferRule).save(ruleRepository::save);
    }

    @Override
    @Transactional
    public void modifyRule(TransRuleModifyCmd command) {
        ruleRepository.ofId(command.getId()).peek(root -> root.modify(command)).save(ruleRepository::save);
    }

}
