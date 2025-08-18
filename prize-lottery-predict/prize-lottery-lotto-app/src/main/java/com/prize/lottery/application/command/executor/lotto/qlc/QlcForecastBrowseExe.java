package com.prize.lottery.application.command.executor.lotto.qlc;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.ForecastAssembler;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.application.vo.QlcForecastVo;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.mapper.QlcMasterMapper;
import com.prize.lottery.po.qlc.QlcIcaiPo;
import com.prize.lottery.po.qlc.QlcMasterRatePo;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QlcForecastBrowseExe {

    private final ForecastAssembler                                assembler;
    private final CloudForecastProperties                          properties;
    private final QlcMasterMapper                                  masterMapper;
    private final ForecastLottoBrowseExecutor                      browseExecutor;
    private final IBrowseForecastRepository<QlcIcaiPo, QlcChannel> browseForecastRepository;

    /**
     * 查看专家预测数据
     *
     * @param userId   用户标识
     * @param masterId 专家标识
     */
    public QlcForecastVo execute(Long userId, String masterId) {
        Period period = browseForecastRepository.latestMasterPeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.QLC);
        browse.lottoForecast(masterId, properties.getExpend(), browseForecastRepository.vipPredicate());
        return browseExecutor.execute(browse, () -> {
            QlcIcaiPo     forecast = browseForecastRepository.getLottoForecast(masterId, period);
            QlcForecastVo detail   = assembler.toQlcVo(forecast);
            this.assembleRate(detail, masterMapper.getLatestMasterRate(masterId));
            return detail;
        });
    }

    private void assembleRate(QlcForecastVo forecast, QlcMasterRatePo rate) {
        forecast.setRed1Hit(rate.getR1Ne());
        forecast.setRed2Hit(rate.getR2Ne());
        forecast.setRed3Hit(rate.getR3Me());
        forecast.setRed12Hit(rate.getR12Me());
        forecast.setRed18Hit(rate.getR18Hi());
        forecast.setRed22Hit(rate.getR22Me());
        forecast.setKill3Hit(rate.getK3Me());
        forecast.setKill6Hit(rate.getK6Ne());
    }
}
