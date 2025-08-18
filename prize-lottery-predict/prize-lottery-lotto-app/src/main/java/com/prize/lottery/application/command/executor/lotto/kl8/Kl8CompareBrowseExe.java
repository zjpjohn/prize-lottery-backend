package com.prize.lottery.application.command.executor.lotto.kl8;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.po.kl8.Kl8IcaiInfoPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Kl8CompareBrowseExe {

    private final CloudForecastProperties                              properties;
    private final ForecastLottoBrowseExecutor                          browseExecutor;
    private final IBrowseForecastRepository<Kl8IcaiInfoPo, Kl8Channel> browseForecastRepository;


    /**
     * 浏览批量对比预测数据
     *
     * @param userId  用户标识
     * @param channel 预测数据字段
     * @param limit   返回数据量
     */
    public List<ICaiRankedDataVo> execute(Long userId, Kl8Channel channel, Integer limit) {
        Period period = browseForecastRepository.latestICaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.KL8);
        browse.batchCompare(properties.getExpend());
        return browseExecutor.execute(browse, () -> browseForecastRepository.getForecastCompareData(period, channel, limit));
    }

}
