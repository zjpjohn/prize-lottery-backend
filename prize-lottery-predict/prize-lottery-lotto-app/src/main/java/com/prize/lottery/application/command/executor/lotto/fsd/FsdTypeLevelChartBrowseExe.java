package com.prize.lottery.application.command.executor.lotto.fsd;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.LottoChartAssembler;
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
import com.prize.lottery.po.fc3d.Fc3dLottoCensusPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.NumberThreeCensusVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FsdTypeLevelChartBrowseExe {

    private final CloudForecastProperties                            properties;
    private final ForecastLottoBrowseExecutor                        browseExecutor;
    private final IBrowseForecastRepository<Fc3dIcaiPo, Fc3dChannel> browseForecastRepository;

    /**
     * 查询最新期的分级全量或vip图表
     *
     * @param userId 用户标识
     * @param type   图表类型
     * @param level  数据分级
     */
    public List<Fc3dLottoCensusPo> execute(Long userId, ChartType type, Integer level) {
        Period period = browseForecastRepository.latestChartPeriod(type);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.FC3D);
        browse.lottoChart(type, properties.getExpend());
        return browseExecutor.execute(browse, () -> browseForecastRepository.getTypLevelFullOrVipChart(type, period, level));
    }

    /**
     * 统一响应数据V1版本
     */
    public FeeDataResult<NumberThreeCensusVo> executeV1(Long userId, ChartType type, Integer level) {
        Period period = browseForecastRepository.latestChartPeriod(type);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.FC3D);
        browse.lottoChart(type, properties.getExpend());
        Pair<Boolean, List<Fc3dLottoCensusPo>> executed = browseExecutor.executeNull(browse, () -> browseForecastRepository.getTypLevelFullOrVipChart(type, period, level));
        if (executed.getKey()) {
            NumberThreeCensusVo census = LottoChartAssembler.assembleFsdChart(executed.getValue());
            return FeeDataResult.success(census, period.getPeriod());
        }
        return FeeDataResult.failure(period.getPeriod(), ForecastExpend.of(properties.getExpend()));
    }
}
