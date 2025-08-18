package com.prize.lottery.application.handler;

import com.lmax.disruptor.EventHandler;
import com.prize.lottery.domain.omit.ability.LotteryOmitAbility;
import com.prize.lottery.domain.omit.ability.executor.*;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.event.CalculatorEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalcLotteryOmitHandler implements EventHandler<CalculatorEvent> {

    private final SumExecutor        sumExecutor;
    private final KuaExecutor        kuaExecutor;
    private final TrendExecutor      trendExecutor;
    private final ItemExecutor       itemExecutor;
    private final MatchExecutor      matchExecutor;
    private final LotteryOmitAbility lotteryOmitAbility;

    @Override
    public void onEvent(CalculatorEvent event, long sequence, boolean endOfBatch) throws Exception {
        LotteryEnum lottery = event.getType();
        try {
            //计算号码基本遗漏
            lotteryOmitAbility.calcOmit(lottery, event.getPeriod());
            if (lottery == LotteryEnum.FC3D || lottery == LotteryEnum.PL3) {
                //计算福彩3D和排列三遗漏数据计算
                sumExecutor.nextOmit(event.getPeriod(), lottery);
                kuaExecutor.nextOmit(event.getPeriod(), lottery);
                trendExecutor.nextOmit(event.getPeriod(), lottery);
                itemExecutor.nextOmit(event.getPeriod(), lottery);
                matchExecutor.nextOmit(event.getPeriod(), lottery);
                return;
            }
            if (lottery == LotteryEnum.PL5) {
                //排列五遗漏数据计算
                sumExecutor.nextOmit(event.getPeriod(), lottery);
                kuaExecutor.nextOmit(event.getPeriod(), lottery);
                itemExecutor.nextOmit(event.getPeriod(), lottery);
            }
        } catch (Exception error) {
            log.error("计算{}遗漏数据错误.", lottery.getNameZh(), error);
        }
    }

}
