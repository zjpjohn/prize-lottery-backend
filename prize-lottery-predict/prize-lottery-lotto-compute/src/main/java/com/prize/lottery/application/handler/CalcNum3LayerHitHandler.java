package com.prize.lottery.application.handler;

import com.lmax.disruptor.EventHandler;
import com.prize.lottery.application.service.INum3LayerService;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.event.CalculatorEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalcNum3LayerHitHandler implements EventHandler<CalculatorEvent> {

    private final INum3LayerService num3LayerService;

    @Override
    public void onEvent(CalculatorEvent event, long sequence, boolean endOfBatch) throws Exception {
        String      period = event.getPeriod();
        LotteryEnum type   = event.getType();
        if (type != LotteryEnum.FC3D && type != LotteryEnum.PL3) {
            return;
        }
        try {
            num3LayerService.calcLayerHit(period, type);
        } catch (Exception error) {
            log.error("计算{}第{}期分层预警分析命中异常:", type.getNameZh(), period, error);
        }
    }
}
