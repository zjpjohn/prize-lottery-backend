package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.dto.ChannelRate;
import com.prize.lottery.dto.N3BestDanDto;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.po.fc3d.Fc3dLottoCensusPo;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import com.prize.lottery.vo.N3Dan3MetricVo;
import com.prize.lottery.vo.Num3MasterCountVo;
import com.prize.lottery.vo.SchemaRenewMasterVo;
import com.prize.lottery.vo.fc3d.Fc3dICaiHitVo;
import com.prize.lottery.vo.fc3d.Fc3dIcaiDataVo;
import com.prize.lottery.vo.fc3d.Fc3dIcaiHistoryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Fc3dIcaiMapper {

    int addFc3dIcaiData(Fc3dIcaiPo fc3dIcaiPo);

    int editFc3dIcaiList(List<Fc3dIcaiPo> fc3dIcais);

    int editForecastMarker(List<Fc3dIcaiPo> fc3dIcais);

    List<Fc3dIcaiPo> getMasterDanMarkers(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    Fc3dIcaiPo getFc3dICaiDataById(Long id);

    Fc3dIcaiPo getFc3dICaiData(@Param("masterId") String masterId, @Param("period") String period);

    Period latestFc3dIcaiPeriod();

    List<String> getUnCalculatedPeriods(@Param("before") String before);

    List<String> getIncrUnCalculatedPeriods();

    List<String> getUnCalcRatePeriods();

    List<String> getIncrUnCalcRatePeriods();

    List<String> getIncrUnCalcRateMasters(String period);

    int hasFc3dICaiData(String period);

    int hasFc3dPeriodOpenCalc(String period);

    int hasFc3dMasterOpenCalc(@Param("masterId") String masterId, @Param("period") String period);

    List<String> getFc3dMasterIdsByPeriod(String period);

    List<String> getUnCalcDataPeriods(String period);

    List<Fc3dIcaiPo> getAllHighRateFc3dDatas(@Param("period") String period,
                                             @Param("last") String last,
                                             @Param("types") List<ChannelRate> types);

    List<Fc3dIcaiPo> getMasterForecasts(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    List<Fc3dIcaiPo> getHighRateFc3dDatas(@Param("period") String period,
                                          @Param("last") String last,
                                          @Param("type") String type,
                                          @Param("rate") Double rate);

    List<Fc3dIcaiPo> getFc3dVipItemDatas(@Param("type") String type,
                                         @Param("period") String period,
                                         @Param("last") String last);

    List<Fc3dIcaiPo> getFc3dRankedItemDatas(@Param("type") String type,
                                            @Param("period") String period,
                                            @Param("last") String last);

    List<Fc3dIcaiDataVo> getFc3dRankedDataList(@Param("type") String type,
                                               @Param("period") String period,
                                               @Param("last") String last,
                                               @Param("limit") Integer limit);

    List<Fc3dIcaiDataVo> getDanFilterForecastList(@Param("period") String period,
                                                  @Param("last") String last,
                                                  @Param("d1") String d1,
                                                  @Param("d2") String d2);

    List<ForecastValue> getFc3dDan3Filter(@Param("period") String period,
                                          @Param("last") String last,
                                          @Param("dans") List<String> dans,
                                          @Param("kills") List<String> kills);

    List<Fc3dIcaiDataVo> getBestDanForecastList(N3BestDanDto data);

    List<Fc3dIcaiDataVo> getMulRankDataList(@Param("period") String period,
                                            @Param("last") String last,
                                            @Param("limit") Integer limit);

    List<Fc3dIcaiPo> getFc3dVipMasterDatas(@Param("period") String period, @Param("last") String last);

    List<Fc3dIcaiPo> getFc3dHotMasterDatas(@Param("period") String period, @Param("last") String last);

    List<String> getFc3dHotMasters(@Param("period") String period,
                                   @Param("last") String last,
                                   @Param("start") String start,
                                   @Param("throttle") Integer throttle);

    int extractFc3dHotMasters(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    List<Fc3dIcaiPo> getAllFc3dIcaiDatas(String period);

    List<Fc3dIcaiPo> getAllUnCalcDatas(String period);

    Period latestMasterFc3dPeriod(String masterId);

    List<ICaiRankedDataVo> getFc3dComparedDatas(@Param("type") String type,
                                                @Param("period") String period,
                                                @Param("last") String last,
                                                @Param("limit") Integer limit);

    List<ICaiRankedDataVo> getAllFc3dRankedDatas(@Param("type") String type,
                                                 @Param("period") String period,
                                                 @Param("last") String last);

    List<ICaiRankedDataVo> getLimitWorstDatas(@Param("type") String type,
                                              @Param("period") String period,
                                              @Param("last") String last,
                                              @Param("limit") Integer limit);

    List<ICaiRankedDataVo> getLimitFc3dRankedDatas(@Param("type") String type,
                                                   @Param("period") String period,
                                                   @Param("last") String last,
                                                   @Param("limit") Integer limit);

    List<ICaiRankedDataVo> getFc3dRankedDatas(PageCondition condition);

    int countFc3dRankedDatas(PageCondition condition);

    List<Fc3dIcaiPo> getFc3dICaiHitsByPeriod(String period);

    List<Fc3dIcaiPo> getFc3dICaiHitsBefore(@Param("masterId") String masterId,
                                           @Param("period") String period,
                                           @Param("limit") Integer limit);

    List<Fc3dIcaiHistoryVo> getHistoryFc3dForecasts(@Param("masterId") String masterId,
                                                    @Param("period") String period,
                                                    @Param("limit") Integer limit);

    Fc3dIcaiPo getMasterFc3dICaiDetail(@Param("masterId") String masterId, @Param("period") String period);

    List<Fc3dIcaiPo> getFc3dICaiListByMasters(@Param("period") String period, @Param("masters") List<String> masters);

    int addFc3dCensus(BaseLottoCensus census);

    int addFc3dCensuses(List<Fc3dLottoCensusPo> censuses);

    List<Fc3dLottoCensusPo> getTypedFc3dCensusList(@Param("period") String period, @Param("type") Integer type);

    List<Fc3dLottoCensusPo> getTypeLeveledCensusList(@Param("period") String period,
                                                     @Param("type") Integer type,
                                                     @Param("level") Integer level);

    List<Fc3dLottoCensusPo> getChannelFc3dCensusList(@Param("period") String period,
                                                     @Param("type") Integer type,
                                                     @Param("channel") String channel);

    String latestFc3dCensusPeriod(Integer type);

    List<SchemaRenewMasterVo> getFc3dSchemaRenewMasters(@Param("period") String period,
                                                        @Param("last") String last,
                                                        @Param("limit") Integer limit);

    List<Fc3dICaiHitVo> getFc3dLast10HitList(@Param("period") String period, @Param("masterId") String masterId);

    List<Fc3dLottoCensusPo> getFc3dVipCensusLevel100(String period);

    List<Fc3dLottoCensusPo> getVipOrItemCensusList(@Param("period") String period,
                                                   @Param("type") Integer type,
                                                   @Param("channels") List<String> channels);

    List<MasterLotteryPo> getNoneDataMasters(String period);

    List<MasterLotteryPo> getAllNoneDataMasters(String period);

    int delDataList(List<String> masterIds);

    List<N3Dan3MetricVo> getN3Dan3Metrics(String period);

    List<Num3MasterCountVo> getHitMasterCounts(@Param("period") String period,
                                               @Param("channel") String channel,
                                               @Param("throttle") Integer throttle,
                                               @Param("limit") Integer limit);

}
