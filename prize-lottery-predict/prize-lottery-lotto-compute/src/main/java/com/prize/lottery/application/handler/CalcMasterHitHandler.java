package com.prize.lottery.application.handler;

import com.lmax.disruptor.EventHandler;
import com.prize.lottery.application.service.IProxyCalcHandler;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.event.CalculatorEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class CalcMasterHitHandler implements EventHandler<CalculatorEvent> {

    private final IProxyCalcHandler proxyCalcHandler;

    @Override
    public void onEvent(CalculatorEvent event, long sequence, boolean endOfBatch) throws Exception {
        try {
            LotteryEnum type   = event.getType();
            String      period = event.getPeriod();
            proxyCalcHandler.calcForecastHit(type, period);
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
    }

}
