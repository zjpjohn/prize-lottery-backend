package com.prize.lottery.application.command.executor.lotto.fsd;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.domain.browse.valobj.ForecastExpend;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class FsdFullOrVipChartBrowseExe {

    private final CloudForecastProperties                            properties;
    private final ForecastLottoBrowseExecutor                        browseExecutor;
    private final IBrowseForecastRepository<Fc3dIcaiPo, Fc3dChannel> browseForecastRepository;

    /**
     * 查看全量图表或vip图表
     *
     * @param userId    用户标识
     * @param chartType 图标类型
     * @param channel   数据分类
     */
    public List<BaseLottoCensus> execute(Long userId, ChartType chartType, Fc3dChannel channel) {
        Period period = browseForecastRepository.latestChartPeriod(chartType);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.FC3D);
        browse.lottoChart(chartType, properties.getExpend());
        return browseExecutor.execute(browse, () -> browseForecastRepository.getForecastFullOrVipChart(chartType, period, channel));
    }

    /**
     * 统一返回付费数据格式
     */
    public <T> FeeDataResult<T> execute(Long userId,
                                        ChartType chartType,
                                        Fc3dChannel channel,
                                        Function<List<BaseLottoCensus>, T> converter) {
        Period period = browseForecastRepository.latestChartPeriod(chartType);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.FC3D);
        browse.lottoChart(chartType, properties.getExpend());
        Pair<Boolean, List<BaseLottoCensus>> executed = browseExecutor.executeNull(browse, () -> browseForecastRepository.getForecastFullOrVipChart(chartType, period, channel));
        if (executed.getKey()) {
            return FeeDataResult.success(converter.apply(executed.getValue()), period.getPeriod());
        }
        return FeeDataResult.failure(period.getPeriod(), ForecastExpend.of(properties.getExpend()));
    }

}
