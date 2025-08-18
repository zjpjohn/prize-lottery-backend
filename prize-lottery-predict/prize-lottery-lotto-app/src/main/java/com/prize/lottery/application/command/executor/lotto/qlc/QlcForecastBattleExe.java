package com.prize.lottery.application.command.executor.lotto.qlc;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.MasterBattleAssembler;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.domain.master.model.MasterBattle;
import com.prize.lottery.domain.master.repository.IMasterBattleRepository;
import com.prize.lottery.domain.master.repository.IMasterInfoRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.po.qlc.QlcIcaiPo;
import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.MasterBattleVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QlcForecastBattleExe {

    private final CloudForecastProperties                          properties;
    private final ForecastLottoBrowseExecutor                      browseExecutor;
    private final MasterBattleAssembler                            battleAssembler;
    private final IMasterBattleRepository                          battleRepository;
    private final IMasterInfoRepository                            masterRepository;
    private final IBrowseForecastRepository<QlcIcaiPo, QlcChannel> browseForecastRepository;

    public MasterBattleVo execute(Long userId, String masterId) {
        //预测期号判断
        Period period = browseForecastRepository.latestICaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        //预测数据
        QlcIcaiPo    forecast = browseForecastRepository.getLottoForecast(masterId, period);
        MasterBattle battle   = battleRepository.ofUk(LotteryEnum.QLC, userId, masterId, period.getPeriod());
        if (battle == null) {
            ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.QLC);
            browse.lottoForecast(masterId, properties.getExpend(), browseForecastRepository.vipPredicate());
            browseExecutor.execute(browse, () -> forecast);

            battle = new MasterBattle(LotteryEnum.QLC, userId, masterId, period.getPeriod());
            battleRepository.save(battle);
        } else if (battle.isRemoved()) {
            battle.reAddBattle();
            battleRepository.save(battle);
        }
        MasterValue master = masterRepository.ofMaster(masterId);
        return battleAssembler.toVo(battle, master, forecast);
    }

}
