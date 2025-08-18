package com.prize.lottery.application.query.service;


import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.application.vo.N3ItemBestTableVo;
import com.prize.lottery.application.vo.N3ItemCensusVo;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.po.pl3.Pl3ComRecommendPo;
import com.prize.lottery.po.pl3.Pl3PivotPo;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.pl3.*;

import java.util.List;
import java.util.Map;

public interface IPl3LottoQueryService {

    Period latestPeriod();

    List<String> lastPeriods(Integer size);

    String latestRank();

    List<Pl3IcaiDataVo> getItemRankedDatas(Pl3Channel channel, String period);

    List<Pl3IcaiDataVo> getMulRankedDatas(String period);

    List<Pl3IcaiDataVo> getBestDanForecasts(N3BestQuery query);

    List<Pl3IcaiDataVo> getDanFilterForecasts(Num3DanFilterQuery query);

    List<ForecastValue> getDan3FilterList(Dan3FilterQuery query);

    Page<ICaiRankedDataVo> getPlsRankedDataList(PlsAdmRankQuery query);

    Page<Pl3ICaiGladVo> getPlsGladList(PageQuery query);

    Page<Pl3MasterMulRankVo> getPlsMasterMulRankList(PageQuery query);

    List<Pl3MasterMulRankVo> getPl3RandomMasters(Integer size, Integer limitRank);

    Page<Pl3MasterRankVo> getPlsRankMasters(PlsRankQuery query);

    Pl3MasterDetail getPlsMasterDetail(String masterId, Long userId);

    List<Pl3IcaiHistoryVo> getPlsMasterHistories(String masterId);

    Map<String, List<HomeMasterVo>> getPlsHomedMasters();

    Page<Pl3MasterSubscribeVo> getPlsSubscribeMasters(SubscribeQuery query);

    Page<LotteryMasterVo> getPl3LotteryMasters(Pl3LottoMasterQuery query);

    SyntheticItemCensusVo itemChart(Pl3Channel channel, String period);

    SyntheticFullCensusVo fullChart(Pl3Channel channel, String period);

    SyntheticVipCensusVo vipChart(Pl3Channel channel, String period);

    NumberThreeCensusVo rateChart(String period);

    NumberThreeCensusVo hotChart(String period);

    List<Pl3MasterSchemaVo> getPl3SchemaMasters(Integer limit);

    Page<Pl3ComRecommendPo> getComRecommendList(ComRecommendQuery query);

    Pl3ComRecommendPo getComRecommend(String period);

    List<SchemaRenewMasterVo> schemaRenewMasters();

    List<MasterBattleVo> getPl3MasterBattles(Long userId);

    Page<MasterBattleRankVo<Pl3MasterMulRankVo>> getPl3BattleMasterRanks(MasterBattleRankQuery query);

    Pl3MasterRateVo getPl3MasterRate(String masterId);

    List<Pl3ICaiHitVo> getPl3MasterBeforeHits(String masterId);

    N3ItemCensusVo itemCensus(String period);

    N3ItemBestTableVo itemTable(String period, Integer limit);

    Page<Pl3PivotPo> getPivotList(PivotListQuery query);

    Pl3PivotPo getPivotInfo(Long id);

    List<String> pivotPeriods();

    List<String> warningPeriods();

    List<N3Dan3MetricVo> dan3Filter(N3DanFilterQuery query);

    List<Num3MasterCountVo> getWholeBestForecasts(N3WholeBestQuery query);

}
