package com.prize.lottery.application.command.executor.lotto.fsd;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FsdCompareBrowseExe {

    private final CloudForecastProperties                            properties;
    private final ForecastLottoBrowseExecutor                        browseExecutor;
    private final IBrowseForecastRepository<Fc3dIcaiPo, Fc3dChannel> browseForecastRepository;

    /**
     * 浏览批量对比预测数据
     *
     * @param userId  用户标识
     * @param channel 预测数据项目
     * @param limit   返回数据量
     */
    public List<ICaiRankedDataVo> execute(Long userId, Fc3dChannel channel, Integer limit) {
        Period period = browseForecastRepository.latestICaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.FC3D);
        browse.batchCompare(properties.getExpend());
        return browseExecutor.execute(browse, () -> browseForecastRepository.getForecastCompareData(period, channel, limit));
    }

}
