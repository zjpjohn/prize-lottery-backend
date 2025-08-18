package com.prize.lottery.application.command.executor.lotto.ssq;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.ForecastAssembler;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.application.vo.SsqForecastVo;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.mapper.SsqMasterMapper;
import com.prize.lottery.po.ssq.SsqIcaiPo;
import com.prize.lottery.po.ssq.SsqMasterRatePo;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsqForecastBrowseExe {

    private final ForecastAssembler                                assembler;
    private final CloudForecastProperties                          properties;
    private final SsqMasterMapper                                  masterMapper;
    private final ForecastLottoBrowseExecutor                      browseExecutor;
    private final IBrowseForecastRepository<SsqIcaiPo, SsqChannel> browseForecastRepository;

    /**
     * 查看专家预测数据
     *
     * @param userId   用户标识
     * @param masterId 专家标识
     */
    public SsqForecastVo execute(Long userId, String masterId) {
        Period period = browseForecastRepository.latestMasterPeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.SSQ);
        browse.lottoForecast(masterId, properties.getExpend(), browseForecastRepository.vipPredicate());
        return browseExecutor.execute(browse, () -> {
            SsqIcaiPo     forecast = browseForecastRepository.getLottoForecast(masterId, period);
            SsqForecastVo detail   = assembler.toSsqVo(forecast);
            this.assembleRate(detail, masterMapper.getLatestMasterRate(masterId));
            return detail;
        });
    }

    private void assembleRate(SsqForecastVo forecast, SsqMasterRatePo rate) {
        forecast.setRed1Hit(rate.getR1Ne());
        forecast.setRed2Hit(rate.getR2Ne());
        forecast.setRed3Hit(rate.getR3Me());
        forecast.setRed12Hit(rate.getR12Ne());
        forecast.setRed20Hit(rate.getR20Me());
        forecast.setRed25Hit(rate.getR25Me());
        forecast.setRk3Hit(rate.getRk3Me());
        forecast.setRk6Hit(rate.getRk6Ne());
        forecast.setBlue3Hit(rate.getB3Ne());
        forecast.setBlue5Hit(rate.getB5Ne());
        forecast.setBkHit(rate.getBkMe());
    }
}
