package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.po.ssq.SsqIcaiPo;
import com.prize.lottery.po.ssq.SsqLottoCensusPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import com.prize.lottery.vo.SchemaRenewMasterVo;
import com.prize.lottery.vo.ssq.SsqICaiHitVo;
import com.prize.lottery.vo.ssq.SsqIcaiHistoryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsqIcaiMapper {

    int addSsqICai(SsqIcaiPo data);

    int editSsqICaiList(List<SsqIcaiPo> datas);

    SsqIcaiPo getSsqICaiDataById(Long id);

    SsqIcaiPo getSsqICaiData(@Param("masterId") String masterId, @Param("period") String period);

    List<SsqIcaiPo> getAllSsqICaiDatas(String period);

    List<SsqIcaiPo> getAllUnCalcDatas(String period);

    int hasSsqICaiData(String period);

    Period latestSsqICaiPeriod();

    Period latestSsqMasterPeriod(String masterId);

    List<String> getUnCalculatedPeriods(@Param("before") String before);

    List<String> getIncrUnCalculatedPeriods();

    List<String> getIncrUnCalcRatePeriods();

    List<String> getIncrUnCalcRateMasters(String period);

    List<String> getUnCalcRatePeriods();

    int hasSsqPeriodOpenCalc(String period);

    int hasSsqMasterOpenCalc(@Param("masterId") String masterId, @Param("period") String period);

    List<String> getSsqMasterIdsByPeriod(String period);

    List<String> getUnCalcDataPeriods(String period);

    List<SsqIcaiPo> getSsqHighRateDatas(@Param("period") String period,
                                        @Param("last") String last,
                                        @Param("type") String type,
                                        @Param("rate") Double rate);

    List<SsqIcaiPo> getSsqHotMasterDatas(@Param("period") String period, @Param("last") String last);

    List<String> getSsqHotMasters(@Param("period") String period,
                                  @Param("last") String last,
                                  @Param("start") String start,
                                  @Param("throttle") Integer throttle);

    int extractSsqHotMasters(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    List<SsqIcaiPo> getSsqVipItemDatas(@Param("type") String type,
                                       @Param("period") String period,
                                       @Param("last") String last);

    List<SsqIcaiPo> getSsqVipMasterDatas(@Param("period") String period, @Param("last") String last);

    List<ICaiRankedDataVo> getAllSsqRankedDatas(@Param("type") String type,
                                                @Param("period") String period,
                                                @Param("last") String last);

    List<ICaiRankedDataVo> getLimitSsqRankedDatas(@Param("type") String type,
                                                  @Param("period") String period,
                                                  @Param("last") String last,
                                                  @Param("limit") Integer limit);

    List<ICaiRankedDataVo> getSsqComparedDatas(@Param("type") String type,
                                               @Param("period") String period,
                                               @Param("last") String last,
                                               @Param("limit") Integer limit);

    List<ICaiRankedDataVo> getSsqRankedDatas(PageCondition condition);

    int countSsqRankedDatas(PageCondition condition);

    List<SsqIcaiPo> getSsqICaiHitsBefore(@Param("masterId") String masterId,
                                         @Param("period") String period,
                                         @Param("limit") Integer limit);

    List<SsqIcaiPo> getSsqICaiHitsByPeriod(String period);

    List<SsqIcaiHistoryVo> getHistorySsqForecasts(@Param("masterId") String masterId,
                                                  @Param("period") String period,
                                                  @Param("limit") Integer limit);

    SsqIcaiPo getMasterSsqICaiDetail(@Param("masterId") String masterId, @Param("period") String period);

    List<SsqIcaiPo> getSsqICaiListByMasters(@Param("period") String period, @Param("masters") List<String> masters);

    int addSsqCensuses(List<SsqLottoCensusPo> censuses);

    List<SsqLottoCensusPo> getTypedSsqCensusList(@Param("period") String period, @Param("type") Integer type);

    List<SsqLottoCensusPo> getChannelSsqCensusList(@Param("period") String period,
                                                   @Param("type") Integer type,
                                                   @Param("channel") String channel);

    List<SsqLottoCensusPo> getTypeLeveledCensusList(@Param("period") String period,
                                                    @Param("type") Integer type,
                                                    @Param("level") Integer level);

    String latestSsqCensusPeriod(Integer type);

    List<SchemaRenewMasterVo> getSsqSchemaRenewMasters(@Param("period") String period,
                                                       @Param("last") String last,
                                                       @Param("limit") Integer limit);

    List<SsqICaiHitVo> getSsqLast10HitList(@Param("period") String period, @Param("masterId") String masterId);

    List<MasterLotteryPo> getNoneDataMasters(String period);

    List<MasterLotteryPo> getAllNoneDataMasters(String period);

    int delDataList(List<String> masterIds);

}
