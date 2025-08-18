package com.prize.lottery.application.handler;

import com.lmax.disruptor.EventHandler;
import com.prize.lottery.domain.omit.ability.LotteryCodeAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.event.CalculatorEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalcLotteryCodeHandler implements EventHandler<CalculatorEvent> {

    private final LotteryCodeAbility lotteryCodeAbility;

    @Override
    public void onEvent(CalculatorEvent event, long sequence, boolean endOfBatch) throws Exception {
        LotteryEnum type   = event.getType();
        String      period = event.getPeriod();
        try {
            lotteryCodeAbility.execute(type, period);
        } catch (Exception error) {
            log.error("计算{}第{}期万能码遗漏错误:", type.getNameZh(), period, error);
        }
    }
}
