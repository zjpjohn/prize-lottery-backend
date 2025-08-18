package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.dto.ChannelRate;
import com.prize.lottery.dto.N3BestDanDto;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.po.pl3.Pl3IcaiPo;
import com.prize.lottery.po.pl3.Pl3LottoCensusPo;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import com.prize.lottery.vo.N3Dan3MetricVo;
import com.prize.lottery.vo.Num3MasterCountVo;
import com.prize.lottery.vo.SchemaRenewMasterVo;
import com.prize.lottery.vo.pl3.Pl3ICaiHitVo;
import com.prize.lottery.vo.pl3.Pl3IcaiDataVo;
import com.prize.lottery.vo.pl3.Pl3IcaiHistoryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Pl3IcaiMapper {

    int addPl3IcaiData(Pl3IcaiPo icai);

    int editPl3IcaiList(List<Pl3IcaiPo> icais);

    int editForecastMarker(List<Pl3IcaiPo> icais);

    Pl3IcaiPo getPl3ICaiDataById(Long id);

    Pl3IcaiPo getPl3ICaiData(@Param("masterId") String masterId, @Param("period") String period);

    List<Pl3IcaiPo> getMasterDanMarkers(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    List<Pl3IcaiPo> getAllPl3ICaiDatas(String period);

    List<Pl3IcaiPo> getAllUnCalcDatas(String period);

    int hasPl3ICaiData(String period);

    Period latestPl3ICaiPeriod();

    Period latestMasterPl3Period(String masterId);

    List<String> getUnCalculatedPeriods(@Param("before") String before);

    List<String> getUnCalcRatePeriods();

    List<String> getIncrUnCalcRatePeriods();

    List<String> getIncrUnCalcRateMasters(String period);

    int hasPl3PeriodOpenCalc(String period);

    int hasPl3MasterOpenCalc(@Param("masterId") String masterId, @Param("period") String period);

    List<String> getPl3MasterIdsByPeriod(String period);

    List<String> getUnCalcDataPeriods(String period);

    List<Pl3IcaiPo> getAllPl3HighRateDatas(@Param("period") String period,
                                           @Param("last") String last,
                                           @Param("types") List<ChannelRate> types);

    List<Pl3IcaiPo> getMasterForecasts(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    List<Pl3IcaiPo> getPl3HighRateDatas(@Param("period") String period,
                                        @Param("last") String last,
                                        @Param("type") String type,
                                        @Param("rate") Double rate);

    List<Pl3IcaiPo> getPl3HotMasterDatas(@Param("period") String period, @Param("last") String last);

    List<String> getPl3HotMasters(@Param("period") String period,
                                  @Param("last") String last,
                                  @Param("start") String start,
                                  @Param("throttle") Integer throttle);

    int extractPl3HotMasters(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    List<Pl3IcaiPo> getPl3VipItemDatas(@Param("type") String type,
                                       @Param("period") String period,
                                       @Param("last") String last);

    List<Pl3IcaiPo> getPl3RankdedItemDatas(@Param("type") String type,
                                           @Param("period") String period,
                                           @Param("last") String last);

    List<Pl3IcaiDataVo> getPl3RankedDataList(@Param("type") String type,
                                             @Param("period") String period,
                                             @Param("last") String last,
                                             @Param("limit") Integer limit);

    List<Pl3IcaiDataVo> getDanFilterForecastList(@Param("period") String period,
                                                 @Param("last") String last,
                                                 @Param("d1") String d1,
                                                 @Param("d2") String d2);

    List<ForecastValue> getPl3Dan3Filter(@Param("period") String period,
                                         @Param("last") String last,
                                         @Param("dans") List<String> dans,
                                         @Param("kills") List<String> kills);

    List<Pl3IcaiDataVo> getBestDanForecastList(N3BestDanDto data);

    List<Pl3IcaiDataVo> getMulRankDataList(@Param("period") String period,
                                           @Param("last") String last,
                                           @Param("limit") Integer limit);

    List<Fc3dIcaiPo> getPl3VipMasterDatas(@Param("period") String period, @Param("last") String last);

    List<ICaiRankedDataVo> getAllPl3RankedDatas(@Param("type") String type,
                                                @Param("period") String period,
                                                @Param("last") String last);

    List<ICaiRankedDataVo> getLimitPl3RankedDatas(@Param("type") String type,
                                                  @Param("period") String period,
                                                  @Param("last") String last,
                                                  @Param("limit") Integer limit);

    List<ICaiRankedDataVo> getPl3ComparedDatas(@Param("type") String type,
                                               @Param("period") String period,
                                               @Param("last") String last,
                                               @Param("limit") Integer limit);

    List<ICaiRankedDataVo> getPl3RankedDatas(PageCondition condition);

    int countPl3RankedDatas(PageCondition condition);

    List<Pl3IcaiPo> getPl3ICaiHitsBefore(@Param("masterId") String masterId,
                                         @Param("period") String period,
                                         @Param("limit") Integer limit);

    List<Pl3IcaiPo> getPl3ICaiHitsByPeriod(String period);

    List<Pl3IcaiHistoryVo> getHistoryPl3Forecasts(@Param("masterId") String masterId,
                                                  @Param("period") String period,
                                                  @Param("limit") Integer limit);

    Pl3IcaiPo getMasterPl3ICaiDetail(@Param("masterId") String masterId, @Param("period") String period);

    List<Pl3IcaiPo> getPl3ICaiListByMasters(@Param("period") String period, @Param("masters") List<String> masters);

    int addPl3Censuses(List<Pl3LottoCensusPo> censuses);

    int addPl3Census(Pl3LottoCensusPo census);

    List<Pl3LottoCensusPo> getTypedPl3CensusList(@Param("period") String period, @Param("type") Integer type);

    List<Pl3LottoCensusPo> getTypeLeveledCensusList(@Param("period") String period,
                                                    @Param("type") Integer type,
                                                    @Param("level") Integer level);

    List<Pl3LottoCensusPo> getChannelPl3CensusList(@Param("period") String period,
                                                   @Param("type") Integer type,
                                                   @Param("channel") String channel);

    String latestPl3CensusPeriod(Integer type);

    List<SchemaRenewMasterVo> getPl3SchemaRenewMasters(@Param("period") String period,
                                                       @Param("last") String last,
                                                       @Param("limit") Integer limit);

    List<Pl3ICaiHitVo> getPl3Last10HitList(@Param("period") String period, @Param("masterId") String masterId);

    List<Pl3LottoCensusPo> getPl3VipCensusLevel100(String period);

    List<Pl3LottoCensusPo> getVipOrItemCensusList(@Param("period") String period,
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
