package com.prize.lottery.application.command.executor;

import com.prize.lottery.application.consumer.event.BalanceDeductEvent;
import com.prize.lottery.domain.expert.repository.IExpertBalanceRepository;
import com.prize.lottery.infrast.persist.enums.ExpertState;
import com.prize.lottery.infrast.props.CloudExpertProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpertIncomeExecutor {

    private final CloudExpertProperties    properties;
    private final IExpertBalanceRepository expertBalanceRepository;

    /**
     * 用户专家收益计算
     */
    @Async
    @Transactional
    public void execute(BalanceDeductEvent command) {
        expertBalanceRepository.ofMaster(command.getMasterId())
                               .filter(e -> e.getState() == ExpertState.ADOPTED)
                               .ifPresent(balance -> {
                                   //收益计算
                                   Long income = properties.getExpertAward().get(command.getLottery()).longValue();
                                   balance.income(command.getSeqNo(), command.getUserId(), command.getPeriod(), command.getLottery(), income);
                                   expertBalanceRepository.save(balance);
                               });
    }

}
