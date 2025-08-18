package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.po.kl8.Kl8IcaiInfoPo;
import com.prize.lottery.po.kl8.Kl8LottoCensusPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ICaiRankedDataVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Kl8IcaiMapper {

    int addKl8IcaiData(Kl8IcaiInfoPo kl8Icai);

    int editKl8IcaiList(List<Kl8IcaiInfoPo> kl8IcaiList);

    Kl8IcaiInfoPo getKl8IcaiDataById(Long id);

    Kl8IcaiInfoPo getKl8IcaiData(@Param("masterId") String masterId,
                                 @Param("period") String period);

    List<Kl8IcaiInfoPo> getAllKl8IcaiDatas(String period);

    int hasKl8IcaiData(String period);

    Period latestKl8IcaiPeriod();

    Period latestKl8MasterPeriod(String masterId);

    int hasKl8PeriodOpenCalc(String period);

    List<String> getUnCalculatedPeriods(String period);

    List<String> getUnCalcRatedPeriod();

    int hasKl8MasterOpenCalc(@Param("period") String period, @Param("masterId") String masterId);

    List<String> getKl8MasterIdsByPeriod(String period);

    List<Kl8IcaiInfoPo> getHighRateKl8Datas(@Param("period") String period,
                                            @Param("last") String last,
                                            @Param("type") String type,
                                            @Param("rate") Double rate);

    List<ICaiRankedDataVo> getKl8ComparedDatas(@Param("type") String type,
                                               @Param("period") String period,
                                               @Param("last") String last,
                                               @Param("limit") Integer limit);

    List<ICaiRankedDataVo> getAllKl8RankedDatas(@Param("type") String type,
                                                @Param("period") String period,
                                                @Param("last") String last);

    List<ICaiRankedDataVo> getKl8RankedDatas(PageCondition condition);

    int countKl8RankedDatas(PageCondition condition);

    List<Kl8IcaiInfoPo> getKl8IcaiHitsBefore(@Param("masterId") String masterId,
                                             @Param("period") String period,
                                             @Param("limit") Integer limit);

    List<Kl8IcaiInfoPo> getKl8IcaiHitsByPeriod(String period);

    List<Kl8IcaiInfoPo> getKl8HistoryForecasts(@Param("masterId") String masterId,
                                               @Param("period") String period,
                                               @Param("limit") Integer limit);

    Kl8IcaiInfoPo getMasterKl8IcaiDetail(@Param("masterId") String masterId,
                                         @Param("period") String period);

    int addKl8Censuses(List<Kl8LottoCensusPo> censuses);

    List<Kl8LottoCensusPo> getTypedKl8CensusList(@Param("period") String period,
                                                 @Param("type") Integer type);

    String latestKl8CensusPeriod(Integer type);

}
