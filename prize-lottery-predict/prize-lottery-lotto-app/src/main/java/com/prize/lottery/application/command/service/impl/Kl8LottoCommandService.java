package com.prize.lottery.application.command.service.impl;

import com.prize.lottery.application.assembler.LottoChartAssembler;
import com.prize.lottery.application.command.executor.lotto.kl8.Kl8CompareBrowseExe;
import com.prize.lottery.application.command.executor.lotto.kl8.Kl8ForecastBrowseExe;
import com.prize.lottery.application.command.executor.lotto.kl8.Kl8FullChartBrowseExe;
import com.prize.lottery.application.command.service.IKl8LottoCommandService;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.po.kl8.Kl8IcaiInfoPo;
import com.prize.lottery.po.kl8.Kl8LottoCensusPo;
import com.prize.lottery.vo.ICaiRankedDataVo;
import com.prize.lottery.vo.kl8.Kl8FullCensusVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Kl8LottoCommandService implements IKl8LottoCommandService {

    @Resource
    private Kl8ForecastBrowseExe  kl8ForecastBrowseExe;
    @Resource
    private Kl8CompareBrowseExe   kl8CompareBrowseExe;
    @Resource
    private Kl8FullChartBrowseExe kl8FullChartBrowseExe;

    @Override
    public Kl8IcaiInfoPo lookupForecast(Long userId, String masterId) {
        return kl8ForecastBrowseExe.execute(userId, masterId);
    }

    @Override
    public Kl8FullCensusVo getFullChartDetail(Long userId, Kl8Channel channel) {
        List<Kl8LottoCensusPo> censuses = kl8FullChartBrowseExe.execute(userId, ChartType.ALL_CHART, channel);
        return LottoChartAssembler.assembleKl8Chart(censuses);
    }

    @Override
    public List<ICaiRankedDataVo> getBatchCompareForecast(Long userId, Kl8Channel channel, Integer limit) {
        return kl8CompareBrowseExe.execute(userId, channel, limit);
    }

}
