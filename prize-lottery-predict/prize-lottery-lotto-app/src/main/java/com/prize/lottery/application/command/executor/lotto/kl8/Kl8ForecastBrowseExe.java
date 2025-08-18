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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Kl8ForecastBrowseExe {

    private final CloudForecastProperties                              properties;
    private final ForecastLottoBrowseExecutor                          browseExecutor;
    private final IBrowseForecastRepository<Kl8IcaiInfoPo, Kl8Channel> browseForecastRepository;

    /**
     * 查看专家预测数据
     *
     * @param userId   用户标识
     * @param masterId 专家标识
     */
    public Kl8IcaiInfoPo execute(Long userId, String masterId) {
        Period period = browseForecastRepository.latestICaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.KL8);
        browse.lottoForecast(masterId, properties.getExpend(), browseForecastRepository.vipPredicate());
        return browseExecutor.execute(browse, () -> browseForecastRepository.getLottoForecast(masterId, period));
    }

}
