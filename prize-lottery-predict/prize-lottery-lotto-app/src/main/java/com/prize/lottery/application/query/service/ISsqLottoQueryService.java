package com.prize.lottery.application.query.service;


import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.ssq.*;

import java.util.List;
import java.util.Map;

public interface ISsqLottoQueryService {

    Period latestPeriod();

    List<String> lastPeriods(Integer size);

    String latestRank();

    Page<ICaiRankedDataVo> getSsqRankedDataList(SsqAdmRankQuery query);

    Page<SsqMasterRankVo> getSsqRankedMasters(SsqRankQuery query);

    Page<SsqICaiGladVo> getSsqGladList(PageQuery query);

    Page<SsqMasterMulRankVo> getSsqMasterMulRankList(MulRankQuery query);

    List<SsqMasterMulRankVo> getSsqRandomMasters(Integer size, Integer limitRank);

    SsqMasterDetail getSsqMasterDetail(String masterId, Long userId);

    List<SsqIcaiHistoryVo> geSsqMasterHistories(String masterId);

    Map<String, List<HomeMasterVo>> getSsqHomedMasters();

    Page<SsqMasterSubscribeVo> getSsqSubscribeMasters(SubscribeQuery query);

    Page<LotteryMasterVo> getSsqLotteryMasters(SsqLottoMasterQuery query);

    SyntheticItemCensusVo itemChart(SsqChannel channel, String period);

    SyntheticFullCensusVo fullChart(SsqChannel channel, String period);

    SyntheticVipCensusVo vipChart(SsqChannel channel, String period);

    SsqChartCensusVo rateChart(String period);

    SsqChartCensusVo hotChart(String period);

    List<SsqMasterSchemaVo> getSsqSchemaMasters(Integer limit);

    List<SchemaRenewMasterVo> schemaRenewMasters();

    List<MasterBattleVo> getSsqMasterBattles(Long userId);

    Page<MasterBattleRankVo<SsqMasterMulRankVo>> getSsqBattleMastersRanks(MasterBattleRankQuery query);

    SsqMasterRateVo getSsqMasterRate(String masterId);

    List<SsqICaiHitVo> getSsqMasterBeforeHits(String masterId);

}
