package com.prize.lottery.application.query.service;


import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.qlc.*;

import java.util.List;
import java.util.Map;

public interface IQlcLottoQueryService {

    Period latestPeriod();

    List<String> lastPeriods(Integer size);

    String latestRank();

    Page<ICaiRankedDataVo> getQlcRankedDataList(QlcAdmRankQuery query);

    Page<QlcICaiGladVo> getQlcGladList(PageQuery query);

    Page<QlcMasterMulRankVo> getQlcMasterMulRankList(PageQuery query);

    List<QlcMasterMulRankVo> getQlcRandomMasters(Integer size, Integer limitRank);

    Page<QlcMasterRankVo> getQlcRankMasters(QlcRankQuery query);

    QlcMasterDetail getQlcMasterDetail(String masterId, Long userId);

    List<QlcIcaiHistoryVo> getQlcMasterHistories(String masterId);

    Map<String, List<HomeMasterVo>> getQlcHomedMasters();

    Page<QlcMasterSubscribeVo> getQlcMasterSubscribeList(SubscribeQuery query);

    Page<LotteryMasterVo> getQlcLotteryMasters(QlcLottoMasterQuery query);

    SyntheticItemCensusVo itemChart(QlcChannel channel, String period);

    SyntheticFullCensusVo fullChart(QlcChannel channel, String period);

    SyntheticVipCensusVo vipChart(QlcChannel channel, String period);

    QlcChartCensusVo rateChart(String period);

    QlcChartCensusVo hotChart(String period);

    List<QlcMasterSchemaVo> getQlcSchemaMasters(Integer limit);

    List<SchemaRenewMasterVo> schemaRenewMasters();

    List<MasterBattleVo> getQlcMasterBattles(Long userId);

    Page<MasterBattleRankVo<QlcMasterMulRankVo>> getQlcBattleMasterRanks(MasterBattleRankQuery query);

    QlcMasterRateVo getQlcMasterRate(String masterId);

    List<QlcICaiHitVo> getQlcMasterBeforeHits(String masterId);

    List<QlcMasterCountVo> getWholeBestForecasts(QlcWholeBestQuery query);

}
