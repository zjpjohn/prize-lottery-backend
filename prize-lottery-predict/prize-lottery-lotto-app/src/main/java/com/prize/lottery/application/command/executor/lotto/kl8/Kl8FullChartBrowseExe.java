package com.prize.lottery.application.command.executor.lotto.kl8;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.po.kl8.Kl8IcaiInfoPo;
import com.prize.lottery.po.kl8.Kl8LottoCensusPo;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Kl8FullChartBrowseExe {

    private final CloudForecastProperties                              properties;
    private final ForecastLottoBrowseExecutor                          browseExecutor;
    private final IBrowseForecastRepository<Kl8IcaiInfoPo, Kl8Channel> browseForecastRepository;


    /**
     * 查看快乐8全量统计图表
     *
     * @param userId  用户标识
     * @param type    图标类型[暂时实现:全量图表]
     * @param channel 数据统计渠道
     */
    public List<Kl8LottoCensusPo> execute(Long userId, ChartType type, Kl8Channel channel) {
        Period period = browseForecastRepository.latestICaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.KL8);
        browse.lottoChart(type, properties.getExpend());
        return browseExecutor.execute(browse, () -> browseForecastRepository.getForecastFullOrVipChart(type, period, channel));
    }

}
