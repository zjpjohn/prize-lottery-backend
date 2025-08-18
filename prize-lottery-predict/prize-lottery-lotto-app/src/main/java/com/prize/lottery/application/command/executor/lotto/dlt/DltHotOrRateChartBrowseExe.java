package com.prize.lottery.application.command.executor.lotto.dlt;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.domain.browse.valobj.ForecastExpend;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.po.dlt.DltIcaiPo;
import com.prize.lottery.po.dlt.DltLottoCensusPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.dlt.DltChartCensusVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DltHotOrRateChartBrowseExe {

    private final CloudForecastProperties                          properties;
    private final ForecastLottoBrowseExecutor                      browseExecutor;
    private final IBrowseForecastRepository<DltIcaiPo, DltChannel> browseForecastRepository;

    /**
     * 查看热门或高命中率专家统计
     *
     * @param userId    用户标识
     * @param chartType 图标类型
     */
    public List<DltLottoCensusPo> execute(Long userId, ChartType chartType) {
        Period period = browseForecastRepository.latestChartPeriod(chartType);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.DLT);
        browse.lottoChart(chartType, properties.getExpend());
        return browseExecutor.execute(browse, () -> browseForecastRepository.getForecastHotOrRateChart(period, chartType));
    }

    /**
     * 统一响应V1格式数据
     */
    public FeeDataResult<DltChartCensusVo> executeV1(Long userId, ChartType chartType) {
        Period period = browseForecastRepository.latestChartPeriod(chartType);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.DLT);
        browse.lottoChart(chartType, properties.getExpend());
        Pair<Boolean, List<DltLottoCensusPo>> executed = browseExecutor.executeNull(browse, () -> browseForecastRepository.getForecastHotOrRateChart(period, chartType));
        if (executed.getKey()) {
            DltChartCensusVo census = LottoChartAssembler.assembleDltChart(executed.getValue());
            return FeeDataResult.success(census, period.getPeriod());
        }
        return FeeDataResult.failure(period.getPeriod(), ForecastExpend.of(properties.getExpend()));
    }

}
