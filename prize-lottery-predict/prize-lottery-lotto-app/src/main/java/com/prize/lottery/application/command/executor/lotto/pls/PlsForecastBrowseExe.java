package com.prize.lottery.application.command.executor.lotto.pls;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.ForecastAssembler;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.application.vo.Pl3ForecastVo;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.mapper.Pl3MasterMapper;
import com.prize.lottery.po.pl3.Pl3IcaiPo;
import com.prize.lottery.po.pl3.Pl3MasterRatePo;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlsForecastBrowseExe {

    private final ForecastAssembler                                assembler;
    private final CloudForecastProperties                          properties;
    private final Pl3MasterMapper                                  masterMapper;
    private final ForecastLottoBrowseExecutor                      browseExecutor;
    private final IBrowseForecastRepository<Pl3IcaiPo, Pl3Channel> browseForecastRepository;

    /**
     * 查看专家预测数据
     *
     * @param userId   用户标识
     * @param masterId 专家标识
     */
    public Pl3ForecastVo execute(Long userId, String masterId) {
        Period period = browseForecastRepository.latestMasterPeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.PL3);
        browse.lottoForecast(masterId, properties.getExpend(), browseForecastRepository.vipPredicate());
        return browseExecutor.execute(browse, () -> {
            Pl3IcaiPo     forecast = browseForecastRepository.getLottoForecast(masterId, period);
            Pl3ForecastVo detail   = this.assembler.toPl3Vo(forecast);
            this.assembleRate(detail, masterMapper.getLatestMasterRate(masterId));
            return detail;
        });
    }

    private void assembleRate(Pl3ForecastVo forecast, Pl3MasterRatePo rate) {
        forecast.setDan1Hit(rate.getD1Ne());
        forecast.setDan2Hit(rate.getD2Ne());
        forecast.setDan3Hit(rate.getD3Me());
        forecast.setCom5Hit(rate.getC5Me());
        forecast.setCom6Hit(rate.getC6Me());
        forecast.setCom7Hit(rate.getC7Hi());
        forecast.setKill1Hit(rate.getK1Hi());
        forecast.setKill2Hit(rate.getK2Me());
        forecast.setComb3Hit(rate.getCb3Ne());
        forecast.setComb4Hit(rate.getCb4Ne());
        forecast.setComb5Hit(rate.getCb5Ne());
    }
}
