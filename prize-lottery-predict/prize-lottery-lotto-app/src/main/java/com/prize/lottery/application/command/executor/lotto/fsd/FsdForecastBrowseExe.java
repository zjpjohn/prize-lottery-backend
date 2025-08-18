package com.prize.lottery.application.command.executor.lotto.fsd;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.ForecastAssembler;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.application.vo.Fc3dForecastVo;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.mapper.Fc3dMasterMapper;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.po.fc3d.Fc3dMasterRatePo;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FsdForecastBrowseExe {

    private final ForecastAssembler                                  assembler;
    private final CloudForecastProperties                            properties;
    private final Fc3dMasterMapper                                   masterMapper;
    private final ForecastLottoBrowseExecutor                        browseExecutor;
    private final IBrowseForecastRepository<Fc3dIcaiPo, Fc3dChannel> browseForecastRepository;

    /**
     * 查看专家预测数据
     *
     * @param userId   用户标识
     * @param masterId 专家标识
     */
    public Fc3dForecastVo execute(Long userId, String masterId) {
        Period period = browseForecastRepository.latestMasterPeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.FC3D);
        browse.lottoForecast(masterId, properties.getExpend(), browseForecastRepository.vipPredicate());
        return browseExecutor.execute(browse, () -> {
            Fc3dIcaiPo     forecast = browseForecastRepository.getLottoForecast(masterId, period);
            Fc3dForecastVo detail   = assembler.toFc3dVo(forecast);
            this.assemblerRate(detail, masterMapper.getLatestMasterRate(masterId));
            return detail;
        });
    }

    private void assemblerRate(Fc3dForecastVo forecast, Fc3dMasterRatePo rate) {
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
