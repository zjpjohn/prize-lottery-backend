package com.prize.lottery.application.command.executor.lotto.fsd;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.MasterBattleAssembler;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.browse.repository.IBrowseForecastRepository;
import com.prize.lottery.domain.master.model.MasterBattle;
import com.prize.lottery.domain.master.repository.IMasterBattleRepository;
import com.prize.lottery.domain.master.repository.IMasterInfoRepository;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.MasterBattleVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FsdForecastBattleExe {

    private final CloudForecastProperties                            properties;
    private final ForecastLottoBrowseExecutor                        browseExecutor;
    private final MasterBattleAssembler                              battleAssembler;
    private final IMasterBattleRepository                            battleRepository;
    private final IMasterInfoRepository                              masterRepository;
    private final IBrowseForecastRepository<Fc3dIcaiPo, Fc3dChannel> browseForecastRepository;

    public MasterBattleVo execute(Long userId, String masterId) {
        //预测期号判断
        Period period = browseForecastRepository.latestICaiPeriod();
        Assert.notNull(period, ResponseHandler.FORECAST_NONE);
        //预测数据
        Fc3dIcaiPo   forecast = browseForecastRepository.getLottoForecast(masterId, period);
        MasterBattle battle   = battleRepository.ofUk(LotteryEnum.FC3D, userId, masterId, period.getPeriod());
        if (battle == null) {
            //对战预测数据前置校验
            ForecastBrowse browse = new ForecastBrowse(userId, period, LotteryEnum.FC3D);
            browse.lottoForecast(masterId, properties.getExpend(), browseForecastRepository.vipPredicate());
            browseExecutor.execute(browse, () -> forecast);
            //保存对战记录
            battle = new MasterBattle(LotteryEnum.FC3D, userId, masterId, period.getPeriod());
        } else if (battle.isRemoved()) {
            //已加入对战但是移除了对战列表，更新对战列表
            battle.reAddBattle();
        }
        battleRepository.save(battle);
        //组装对战数据
        MasterValue master = masterRepository.ofMaster(masterId);
        return battleAssembler.toVo(battle, master, forecast);
    }

}
