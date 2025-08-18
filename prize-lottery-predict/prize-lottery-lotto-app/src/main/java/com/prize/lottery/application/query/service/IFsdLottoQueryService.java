package com.prize.lottery.application.query.service;


import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.application.vo.N3ItemBestTableVo;
import com.prize.lottery.application.vo.N3ItemCensusVo;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.po.fc3d.Fc3dComRecommendPo;
import com.prize.lottery.po.fc3d.Fc3dPivotPo;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.fc3d.*;

import java.util.List;
import java.util.Map;

public interface IFsdLottoQueryService {

    Period latestPeriod();

    List<String> lastPeriods(Integer size);

    String latestRank();

    List<Fc3dIcaiDataVo> getItemRankedDatas(Fc3dChannel channel, String period);

    List<Fc3dIcaiDataVo> getMulRankedDatas(String period);

    List<Fc3dIcaiDataVo> getBestDanForecasts(N3BestQuery query);

    List<Fc3dIcaiDataVo> getDanFilterForecasts(Num3DanFilterQuery query);

    List<ForecastValue> getDan3FilterList(Dan3FilterQuery query);

    Page<ICaiRankedDataVo> getFsdRankedDataList(FsdAdmRankQuery query);

    Page<Fc3dICaiGladVo> getFc3dGladList(PageQuery query);

    Page<Fc3dMasterRankVo> getFc3dRankMasters(FsdRankQuery query);

    Page<Fc3dMasterMulRankVo> getFc3dMasterMulRanks(PageQuery query);

    List<Fc3dMasterMulRankVo> getFc3dRandomMasters(Integer size, Integer limitRank);

    Page<LotteryMasterVo> getFc3dLotteryMasters(FsdLottoMasterQuery query);

    Fc3dMasterDetail getFsdMasterDetail(String masterId, Long userId);

    List<Fc3dIcaiHistoryVo> getFsdMasterHistories(String masterId);

    Map<String, List<HomeMasterVo>> getFsdHomedMasters();

    Page<Fc3dMasterSubscribeVo> getFsdSubscribeMasters(SubscribeQuery query);

    SyntheticItemCensusVo itemChart(Fc3dChannel channel, String period);

    SyntheticFullCensusVo fullChart(Fc3dChannel channel, String period);

    SyntheticVipCensusVo vipChart(Fc3dChannel channel, String period);

    NumberThreeCensusVo rateChart(String period);

    NumberThreeCensusVo hotChart(String period);

    List<Fc3dMasterSchemaVo> fc3dSchemaMasters(Integer limit);

    Page<Fc3dComRecommendPo> getFc3dComRecommends(ComRecommendQuery query);

    Fc3dComRecommendPo getComRecommend(String period);

    List<SchemaRenewMasterVo> schemaRenewMasters();

    List<MasterBattleVo> getFc3dMasterBattles(Long userId);

    Page<MasterBattleRankVo<Fc3dMasterMulRankVo>> getFc3dBattleMasterRanks(MasterBattleRankQuery query);

    Fc3dMasterRateVo getFc3dMasterRate(String masterId);

    List<Fc3dICaiHitVo> getFc3dMasterBeforeHits(String masterId);

    N3ItemCensusVo itemCensus(String period);

    N3ItemBestTableVo itemTable(String period, Integer limit);

    Page<Fc3dPivotPo> getPivotList(PivotListQuery query);

    Fc3dPivotPo getPivotInfo(Long id);

    List<String> warningPeriods();

    List<String> pivotPeriods();

    List<N3Dan3MetricVo> dan3Filter(N3DanFilterQuery query);

    List<Num3MasterCountVo> getWholeBestForecasts(N3WholeBestQuery query);

}
