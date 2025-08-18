package com.prize.lottery.application.handler;

import com.lmax.disruptor.EventHandler;
import com.prize.lottery.domain.lottery.ability.LotteryDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.event.CalculatorEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalcLotteryNum3Handler implements EventHandler<CalculatorEvent> {

    private final LotteryDomainAbility lotteryDomainAbility;

    @Override
    public void onEvent(CalculatorEvent event, long sequence, boolean endOfBatch) throws Exception {
        LotteryEnum type   = event.getType();
        String      period = event.getPeriod();
        try {
            lotteryDomainAbility.calcNum3Same(type, period);
        } catch (Exception error) {
            log.error("计算选三型{}号码第{}期组选上次同开奖错误:", type.getNameZh(), period, error);
        }
    }

}
