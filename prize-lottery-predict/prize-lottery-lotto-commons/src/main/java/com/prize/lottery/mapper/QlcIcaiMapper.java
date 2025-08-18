package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.po.qlc.QlcIcaiPo;
import com.prize.lottery.po.qlc.QlcLottoCensusPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import com.prize.lottery.vo.SchemaRenewMasterVo;
import com.prize.lottery.vo.qlc.QlcICaiHitVo;
import com.prize.lottery.vo.qlc.QlcIcaiHistoryVo;
import com.prize.lottery.vo.qlc.QlcMasterCountVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QlcIcaiMapper {

    int addQlcICai(QlcIcaiPo data);

    int editQlcICaiList(List<QlcIcaiPo> datas);

    QlcIcaiPo getQlcICaiDataById(Long id);

    QlcIcaiPo getQlcICaiData(@Param("masterId") String masterId, @Param("period") String period);

    List<QlcIcaiPo> getAllQlcICaiDatas(String period);

    List<QlcIcaiPo> getAllUnCalcDatas(String period);

    int hasQlcICaiData(String period);

    Period latestQlcICaiPeriod();

    Period latestQlcMasterPeriod(String masterId);

    int hasQlcPeriodOpenCalc(String period);

    List<String> getUnCalculatedPeriods(@Param("before") String before);

    List<String> getIncrUnCalculatedPeriods();

    List<String> getIncrUnCalcRatePeriods();

    List<String> getUnCalcRatePeriods();

    List<String> getIncrUnCalcRateMasters(String period);

    int hasQlcMasterOpenCalc(@Param("masterId") String masterId, @Param("period") String period);

    List<String> getQlcMasterIdsByPeriod(String period);

    List<String> getUnCalcDataPeriods(String period);

    List<QlcIcaiPo> getQlcHighRateDatas(@Param("period") String period,
                                        @Param("last") String last,
                                        @Param("type") String type,
                                        @Param("rate") Double rate);

    List<QlcIcaiPo> getMasterForecasts(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    List<QlcIcaiPo> getQlcHotMasterDatas(@Param("period") String period, @Param("last") String last);

    List<String> getQlcHotMasters(@Param("period") String period,
                                  @Param("last") String last,
                                  @Param("start") String start,
                                  @Param("throttle") Integer throttle);

    int extractQlcHotMasters(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    List<QlcIcaiPo> getQlcVipItemsDatas(@Param("type") String type,
                                        @Param("period") String period,
                                        @Param("last") String last);

    List<QlcIcaiPo> getQlcVipMasterDatas(@Param("period") String period, @Param("last") String last);

    List<ICaiRankedDataVo> getAllQlcRankedDatas(@Param("period") String period,
                                                @Param("last") String last,
                                                @Param("type") String type);

    List<ICaiRankedDataVo> getLimitQlcRankedDatas(@Param("period") String period,
                                                  @Param("last") String last,
                                                  @Param("type") String type,
                                                  @Param("limit") Integer limit);

    List<ICaiRankedDataVo> getQlcComparedDatas(@Param("period") String period,
                                               @Param("last") String last,
                                               @Param("type") String type,
                                               @Param("limit") Integer limit);

    List<ICaiRankedDataVo> getQlcRankedDatas(PageCondition condition);

    int countQlcRankedDatas(PageCondition condition);

    List<QlcIcaiPo> getQlcICaiHitsBefore(@Param("masterId") String masterId,
                                         @Param("period") String period,
                                         @Param("limit") Integer limit);

    List<QlcIcaiPo> getQlcICaiHitsByPeriod(String period);

    List<QlcIcaiHistoryVo> getHistoryQlcForecasts(@Param("masterId") String masterId,
                                                  @Param("period") String period,
                                                  @Param("limit") Integer limit);

    QlcIcaiPo getMasterQlcICaiDetail(@Param("masterId") String masterId, @Param("period") String period);

    List<QlcIcaiPo> getQlcICaiListByMasters(@Param("period") String period, @Param("masters") List<String> masters);

    int addQlcCensuses(List<QlcLottoCensusPo> censuses);

    List<QlcLottoCensusPo> getTypedQlcCensusList(@Param("period") String period, @Param("type") Integer type);

    List<QlcLottoCensusPo> getChannelQlcCensusList(@Param("period") String period,
                                                   @Param("type") Integer type,
                                                   @Param("channel") String channel);

    List<QlcLottoCensusPo> getTypeLeveledCensusList(@Param("period") String period,
                                                    @Param("type") Integer type,
                                                    @Param("level") Integer level);

    String latestQlcCensusPeriod(Integer type);

    List<SchemaRenewMasterVo> getQlcSchemaRenewMasters(@Param("period") String period,
                                                       @Param("last") String last,
                                                       @Param("limit") Integer limit);

    List<QlcICaiHitVo> getQlcLast10HitList(@Param("period") String period, @Param("masterId") String masterId);

    List<MasterLotteryPo> getNoneDataMasters(String period);

    List<MasterLotteryPo> getAllNoneDataMasters(String period);

    int delDataList(List<String> masterIds);

    List<QlcMasterCountVo> getHitMasterCounts(@Param("period") String period,
                                              @Param("channel") String channel,
                                              @Param("throttle") Integer throttle,
                                              @Param("limit") Integer limit);

}
