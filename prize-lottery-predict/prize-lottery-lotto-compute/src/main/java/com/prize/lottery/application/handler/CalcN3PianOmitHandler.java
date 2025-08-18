package com.prize.lottery.application.handler;

import com.lmax.disruptor.EventHandler;
import com.prize.lottery.domain.omit.ability.LottoN3PianAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.event.CalculatorEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalcN3PianOmitHandler implements EventHandler<CalculatorEvent> {

    private final LottoN3PianAbility lottoPianAbility;

    @Override
    public void onEvent(CalculatorEvent event, long sequence, boolean endOfBatch) throws Exception {
        LotteryEnum type = event.getType();
        try {
            if (type == LotteryEnum.FC3D || type == LotteryEnum.PL3) {
                lottoPianAbility.execute(type, event.getPeriod());
            }
        } catch (Exception error) {
            log.error("计算{}偏态遗漏异常.", type.getNameZh(), error);
        }
    }

}
