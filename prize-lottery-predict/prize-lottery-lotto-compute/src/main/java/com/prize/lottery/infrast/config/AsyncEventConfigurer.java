package com.prize.lottery.infrast.config;

import com.prize.lottery.application.handler.*;
import com.prize.lottery.event.EventBus;
import com.prize.lottery.infrast.event.CalculatorEvent;
import com.prize.lottery.infrast.event.StatsCalcEvent;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AsyncEventConfigurer {

    public static final String CALCULATE_PRODUCER = "calculateProducer";
    public static final String STATISTIC_PRODUCER = "statisticProducer";

    @Configuration
    public static class CalcAsyncConfigurer {

        @Resource
        private CalcMasterHitHandler     calcMasterHitHandler;
        @Resource
        private CalcMasterRateHandler    calcMasterRateHandler;
        @Resource
        private CalcMasterRankHandler    calcMasterRankHandler;
        @Resource
        private ExtractVipMasterHandler  extractVipMasterHandler;
        @Resource
        private ExtractHomeMasterHandler extractHomeMasterHandler;
        @Resource
        private CalcWarningHitHandler    calcWarningHitHandler;
        @Resource
        private ExtractMasterGladHandler extractMasterGladHandler;
        @Resource
        private CalcLotteryOmitHandler   calcLotteryOmitHandler;
        @Resource
        private CalcLotteryCodeHandler   calcLotteryCodeHandler;
        @Resource
        private CalcLotteryNum3Handler   calcLotteryNum3Handler;
        @Resource
        private CalcLotteryDanHandler    calcLotteryDanHandler;
        @Resource
        private CalcLotteryOttHandler    calcLotteryOttHandler;
        @Resource
        private CalcN3PivotHitHandler    calcN3PivotHitHandler;
        @Resource
        private CalcLottoAroundHandler   calcLottoAroundHandler;
        @Resource
        private CalcNum3WarnHitHandler   calcNum3WarnHitHandler;
        @Resource
        private CalcNum3LayerHitHandler  calcNum3LayerHitHandler;

        @Bean(name = CALCULATE_PRODUCER)
        public EventBus<CalculatorEvent> calculateProducer() {
            EventBus<CalculatorEvent> eventBus = new EventBus<>(CalculatorEvent.class);
            //计算预警分析命中
            eventBus.handleWith(calcWarningHitHandler);
            //计算选三组选预警分析命中
            eventBus.handleWith(calcNum3WarnHitHandler);
            //计算选三分层预警分析命中
            eventBus.handleWith(calcNum3LayerHitHandler);
            //计算开奖数据遗漏
            eventBus.handleWith(calcLotteryOmitHandler);
            //计算开奖数据万能码遗漏
            eventBus.handleWith(calcLotteryCodeHandler);
            //计算开奖数据胆码遗漏
            eventBus.handleWith(calcLotteryDanHandler);
            //计算开奖数据012遗漏
            eventBus.handleWith(calcLotteryOttHandler);
            //计算今日要点命中
            eventBus.handleWith(calcN3PivotHitHandler);
            //计算绕胆图命中结果
            eventBus.handleWith(calcLottoAroundHandler);
            //计算最新开奖最新历史相同命中期号
            eventBus.handleWith(calcLotteryNum3Handler);
            //专家预测数据计算
            eventBus.handleWith(calcMasterHitHandler)//计算专家预测数据命中
                    .then(calcMasterRateHandler)//计算专家预测命中率
                    .then(calcMasterRankHandler)//计算专家排名
                    .then(extractVipMasterHandler,//计算vip转件
                          extractHomeMasterHandler,//计算首页专家
                          extractMasterGladHandler);//提取首页中奖专家

            return eventBus;
        }
    }

    @Configuration
    public static class CensusAsyncConfigurer {

        @Resource
        public  CensusAllMasterHandler  censusAllMasterHandler;
        @Resource
        private CensusHotMasterHandler  censusHotMasterHandler;
        @Resource
        private CensusVipMasterHandler  censusVipMasterHandler;
        @Resource
        private CensusRateMasterHandler censusRateMasterHandler;
        @Resource
        private CensusLottoIndexHandler censusLottoIndexHandler;
        @Resource
        private CensusNum3IndexHandler  censusNum3IndexHandler;
        @Resource
        private CensusItemMasterHandler censusItemMasterHandler;

        @Bean(name = STATISTIC_PRODUCER)
        public EventBus<StatsCalcEvent> statisticProducer() {
            EventBus<StatsCalcEvent> eventBus = new EventBus<>(StatsCalcEvent.class);
            eventBus.handleWith(censusAllMasterHandler, censusVipMasterHandler, censusHotMasterHandler, censusRateMasterHandler, censusItemMasterHandler)
                    .then(censusLottoIndexHandler, censusNum3IndexHandler);
            return eventBus;
        }

    }

}
