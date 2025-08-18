package com.prize.lottery.application.command.service;


import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.po.kl8.Kl8IcaiInfoPo;
import com.prize.lottery.vo.ICaiRankedDataVo;
import com.prize.lottery.vo.kl8.Kl8FullCensusVo;

import java.util.List;

public interface IKl8LottoCommandService {

    Kl8IcaiInfoPo lookupForecast(Long userId, String masterId);

    Kl8FullCensusVo getFullChartDetail(Long userId, Kl8Channel channel);

    List<ICaiRankedDataVo> getBatchCompareForecast(Long userId, Kl8Channel channel, Integer limit);

}
