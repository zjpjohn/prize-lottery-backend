package com.prize.lottery.application.handler;

import com.lmax.disruptor.EventHandler;
import com.prize.lottery.domain.lottery.ability.AroundDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.event.CalculatorEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalcLottoAroundHandler implements EventHandler<CalculatorEvent> {

    private final AroundDomainAbility aroundDomainAbility;

    @Override
    public void onEvent(CalculatorEvent event, long sequence, boolean endOfBatch) throws Exception {
        LotteryEnum type   = event.getType();
        String      period = event.getPeriod();
        if (type != LotteryEnum.FC3D && type != LotteryEnum.PL3) {
            return;
        }
        try {
            aroundDomainAbility.calcAroundResult(type, period);
        } catch (Exception error) {
            log.error("计算{}第{}期绕胆图错误:", type.getNameZh(), period, error);
        }
    }

}
