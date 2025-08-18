package com.prize.lottery.application.command.executor.lotto.ssq;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.domain.browse.valobj.ForecastExpend;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.po.ssq.SsqIcaiPo;
import com.prize.lottery.po.ssq.SsqLottoCensusPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ssq.SsqChartCensusVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsqTypeLevelChartBrowseExe {

    private final CloudForecastProperties                          properties;
    private final ForecastLottoBrowseExecutor                      browseExecutor;
    private final IBrowseForecastRepository<SsqIcaiPo, SsqChannel> browseForecastRepository;

    /**
     * 查看双色球分级全量或vip图表
     *
     * @param userId    用户标识
     * @param chartType 图标类型
     * @param level     分级级别
     */
    public List<SsqLottoCensusPo> execute(Long userId, ChartType chartType, Integer level) {
        Period period = browseForecastRepository.latestChartPeriod(chartType);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.SSQ);
        browse.lottoChart(chartType, properties.getExpend());
        return browseExecutor.execute(browse, () -> browseForecastRepository.getTypLevelFullOrVipChart(chartType, period, level));
    }

    public FeeDataResult<SsqChartCensusVo> executeV1(Long userId, ChartType chartType, Integer level) {
        Period period = browseForecastRepository.latestChartPeriod(chartType);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.SSQ);
        browse.lottoChart(chartType, properties.getExpend());
        Pair<Boolean, List<SsqLottoCensusPo>> executed = browseExecutor.executeNull(browse, () -> browseForecastRepository.getTypLevelFullOrVipChart(chartType, period, level));
        if (executed.getKey()) {
            SsqChartCensusVo census = LottoChartAssembler.assembleSsqChart(executed.getValue());
            return FeeDataResult.success(census, period.getPeriod());
        }
        return FeeDataResult.failure(period.getPeriod(), ForecastExpend.of(properties.getExpend()));
    }

}
