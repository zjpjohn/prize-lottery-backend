package com.prize.lottery.application.command.executor.lotto.dlt;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.MasterBattleAssembler;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.domain.master.model.MasterBattle;
import com.prize.lottery.domain.master.repository.IMasterBattleRepository;
import com.prize.lottery.domain.master.repository.IMasterInfoRepository;
import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.po.dlt.DltIcaiPo;
import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.MasterBattleVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DltForecastBattleExe {

    private final CloudForecastProperties                          properties;
    private final ForecastLottoBrowseExecutor                      browseExecutor;
    private final MasterBattleAssembler                            battleAssembler;
    private final IMasterBattleRepository                          battleRepository;
    private final IMasterInfoRepository                            masterRepository;
    private final IBrowseForecastRepository<DltIcaiPo, DltChannel> browseForecastRepository;

    public MasterBattleVo execute(Long userId, String masterId) {
        //预测期号判断
        Period period = browseForecastRepository.latestICaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        //预测数据
        DltIcaiPo    forecast = browseForecastRepository.getLottoForecast(masterId, period);
        MasterBattle battle   = battleRepository.ofUk(LotteryEnum.DLT, userId, masterId, period.getPeriod());
        if (battle == null) {
            ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.DLT);
            browse.lottoForecast(masterId, properties.getExpend(), browseForecastRepository.vipPredicate());
            browseExecutor.execute(browse, () -> forecast);
            battle = new MasterBattle(LotteryEnum.DLT, userId, masterId, period.getPeriod());
        } else if (battle.isRemoved()) {
            battle.reAddBattle();
        }
        battleRepository.save(battle);
        MasterValue master = masterRepository.ofMaster(masterId);
        return battleAssembler.toVo(battle, master, forecast);
    }

}
