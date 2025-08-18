package com.prize.lottery.application.handler;

import com.lmax.disruptor.EventHandler;
import com.prize.lottery.application.service.IFc3dICaiService;
import com.prize.lottery.application.service.IPl3ICaiService;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.event.CalculatorEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalcN3PivotHitHandler implements EventHandler<CalculatorEvent> {

    private final IFc3dICaiService fc3dICaiService;
    private final IPl3ICaiService  pl3ICaiService;

    @Override
    public void onEvent(CalculatorEvent event, long sequence, boolean endOfBatch) throws Exception {
        LotteryEnum type   = event.getType();
        String      period = event.getPeriod();
        try {
            if (type == LotteryEnum.FC3D) {
                fc3dICaiService.calcPivotHit(period);
                return;
            }
            if (type == LotteryEnum.PL3) {
                pl3ICaiService.calcPivotHit(period);
            }
        } catch (Exception error) {
            log.error("计算{}第{}期今日要点错误:", type.getNameZh(), period, error);
        }
    }
}
