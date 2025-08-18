package com.prize.lottery.application.query.service;


import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.vo.MasterBattleRankVo;
import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.*;
import com.prize.lottery.vo.dlt.*;

import java.util.List;
import java.util.Map;

public interface IDltLottoQueryService {

    Period latestPeriod();

    List<String> lastPeriods(Integer size);

    String latestRank();

    Page<ICaiRankedDataVo> getDltRankedDataList(DltAdmRankQuery query);

    Page<DltICaiGladVo> getDltGladList(PageQuery query);

    Page<DltMasterMulRankVo> getDltMasterMulRankList(MulRankQuery query);

    List<DltMasterMulRankVo> getDltRandomMasters(Integer size, Integer limitRank);

    Page<DltMasterRankVo> getDltRankedMasters(DltRankQuery query);

    List<DltIcaiHistoryVo> getDltMasterHistories(String masterId);

    DltMasterDetail getDltMasterDetail(String masterId, Long userId);

    Map<String, List<HomeMasterVo>> getDltHomedMasters();

    Page<DltMasterSubscribeVo> getDltSubscribeMasters(SubscribeQuery query);

    Page<LotteryMasterVo> getDltLotteryMasters(DltLottoMasterQuery query);

    SyntheticItemCensusVo itemChart(DltChannel channel, String period);

    SyntheticFullCensusVo fullChart(DltChannel channel, String period);

    SyntheticVipCensusVo vipChart(DltChannel channel, String period);

    DltChartCensusVo rateChart(String period);

    DltChartCensusVo hotChart(String period);

    List<DltMasterSchemaVo> getDltSchemaMasters(Integer limit);

    List<SchemaRenewMasterVo> schemaRenewMasters();

    List<MasterBattleVo> getDltMasterBattles(Long userId);

    Page<MasterBattleRankVo<DltMasterMulRankVo>> getDltBattleMasterRanks(MasterBattleRankQuery query);

    DltMasterRateVo getDltMasterRate(String masterId);

    List<DltICaiHitVo> getDltMasterBeforeHits(String masterId);

}
