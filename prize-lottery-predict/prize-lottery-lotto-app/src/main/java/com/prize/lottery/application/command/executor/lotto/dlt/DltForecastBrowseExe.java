package com.prize.lottery.application.command.executor.lotto.dlt;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.ForecastAssembler;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.application.vo.DltForecastVo;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.mapper.DltMasterMapper;
import com.prize.lottery.po.dlt.DltIcaiPo;
import com.prize.lottery.po.dlt.DltMasterRatePo;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DltForecastBrowseExe {

    private final ForecastAssembler                                assembler;
    private final CloudForecastProperties                          properties;
    private final DltMasterMapper                                  masterMapper;
    private final ForecastLottoBrowseExecutor                      browseExecutor;
    private final IBrowseForecastRepository<DltIcaiPo, DltChannel> browseForecastRepository;

    /**
     * 查看专家预测数据
     *
     * @param userId   用户标识
     * @param masterId 专家标识
     */
    public DltForecastVo execute(Long userId, String masterId) {
        Period period = browseForecastRepository.latestMasterPeriod(masterId);
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);

        ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.DLT);
        browse.lottoForecast(masterId, properties.getExpend(), browseForecastRepository.vipPredicate());
        return browseExecutor.execute(browse, () -> {
            //获取预测数据
            DltIcaiPo     forecast = browseForecastRepository.getLottoForecast(masterId, period);
            DltForecastVo detail   = assembler.toDltVo(forecast);
            //装配对应字段近期命中率信息
            this.assembleRate(detail, masterMapper.getMasterLatestRate(masterId));
            return detail;
        });
    }

    /**
     * 装配近期预测命中率
     */
    private void assembleRate(DltForecastVo forecast, DltMasterRatePo rate) {
        forecast.setRed1Hit(rate.getR1Ne());
        forecast.setRed2Hit(rate.getR2Ne());
        forecast.setRed3Hit(rate.getR3Me());
        forecast.setRed10Hit(rate.getR10Ne());
        forecast.setRed20Hit(rate.getR20Ne());
        forecast.setRk3Hit(rate.getRk3Me());
        forecast.setRk6Hit(rate.getRk6Ne());
        forecast.setBlue1Hit(rate.getB1Ne());
        forecast.setBlue2Hit(rate.getB2Ne());
        forecast.setBlue6Hit(rate.getB6Me());
        forecast.setBkHit(rate.getBkMe());
    }
}
