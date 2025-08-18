package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.dlt.DltIcaiPo;
import com.prize.lottery.po.dlt.DltLottoCensusPo;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import com.prize.lottery.vo.SchemaRenewMasterVo;
import com.prize.lottery.vo.dlt.DltICaiHitVo;
import com.prize.lottery.vo.dlt.DltIcaiHistoryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DltIcaiMapper {

    int addDltICai(DltIcaiPo icai);

    int editDltICaiList(List<DltIcaiPo> datas);

    DltIcaiPo getDltICaiDataById(Long id);

    DltIcaiPo getDltICaiData(@Param("masterId") String masterId, @Param("period") String period);

    List<DltIcaiPo> getAllDltICaiDatas(String period);

    List<DltIcaiPo> getAllUnCalcDatas(String period);

    int hasDltICaiData(String period);

    Period latestDltICaiPeriod();

    Period latestDltMasterPeriod(String masterId);

    List<String> getUnCalculatedPeriods(@Param("before") String before);

    List<String> getIncrUnCalculatedPeriods();

    List<String> getIncrUnCalcRatePeriods();

    List<String> getIncrUnCalcRateMasters(String period);

    List<String> getUnCalcRatePeriods();

    int hasDltPeriodOpenCalc(String period);

    int hasDltMasterOpenCalc(@Param("masterId") String masterId, @Param("period") String period);

    List<String> getDltMasterIdsByPeriod(String period);

    List<String> getUnCalcDataPeriods(String period);

    List<DltIcaiPo> getDltHighRateDatas(@Param("type") String type, @Param("period") String period,
        @Param("last") String last, @Param("rate") double rate);

    List<DltIcaiPo> getDltHotMasterDatas(@Param("period") String period, @Param("last") String last);

    List<String> getDltHotMasters(@Param("period") String period, @Param("last") String last,
        @Param("start") String start, @Param("throttle") Integer throttle);

    int extractDltHotMasters(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    List<DltIcaiPo> getDltVipItemDatas(@Param("type") String type, @Param("period") String period,
        @Param("last") String last);

    List<DltIcaiPo> getDltVipMasterDatas(@Param("period") String period, @Param("last") String last);

    List<ICaiRankedDataVo> getAllDltRankedDatas(@Param("type") String type, @Param("period") String period,
                                                @Param("last") String last);

    List<ICaiRankedDataVo> getLimitDltRankedDatas(@Param("type") String type, @Param("period") String period,
        @Param("last") String last, @Param("limit") Integer limit);

    List<ICaiRankedDataVo> getDltComparedDatas(@Param("type") String type, @Param("period") String period,
        @Param("last") String last, @Param("limit") Integer limit);

    List<ICaiRankedDataVo> getDltRankedDatas(PageCondition condition);

    int countDltRankedDatas(PageCondition condition);

    List<DltIcaiPo> getDltICaiHitsBefore(@Param("masterId") String masterId, @Param("period") String period,
        @Param("limit") Integer limit);

    List<DltIcaiPo> getDltICaiHitsByPeriod(String period);

    List<DltIcaiHistoryVo> getHistoryDltForecasts(@Param("masterId") String masterId, @Param("period") String period,
                                                  @Param("limit") Integer limit);

    DltIcaiPo getMasterDltICaiDetail(@Param("masterId") String masterId, @Param("period") String period);

    List<DltIcaiPo> getDltICaiListByMasters(@Param("period") String period, @Param("masters") List<String> masters);

    int addDltCensuses(List<DltLottoCensusPo> censuses);

    List<DltLottoCensusPo> getTypedDltCensusList(@Param("period") String period, @Param("type") Integer type);

    List<DltLottoCensusPo> getTypeLeveledCensusList(@Param("period") String period, @Param("type") Integer type,
        @Param("level") Integer level);

    List<DltLottoCensusPo> getChannelDltCensusList(@Param("period") String period, @Param("type") Integer type,
        @Param("channel") String channel);

    String latestDltCensusPeriod(Integer type);

    List<SchemaRenewMasterVo> getDltSchemaRenewMasters(@Param("period") String period, @Param("last") String last,
                                                       @Param("limit") Integer limit);

    List<DltICaiHitVo> getDltLast10HitList(@Param("period") String period, @Param("masterId") String masterId);

    List<MasterLotteryPo> getNoneDataMasters(String period);

    List<MasterLotteryPo> getAllNoneDataMasters(String period);

    int delDataList(List<String> masterIds);

}
