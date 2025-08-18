package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.dto.WorseMasterFilter;
import com.prize.lottery.po.dlt.DltHomeMasterPo;
import com.prize.lottery.po.dlt.DltMasterRankPo;
import com.prize.lottery.po.dlt.DltMasterRatePo;
import com.prize.lottery.vo.HomeMasterVo;
import com.prize.lottery.vo.LotteryMasterVo;
import com.prize.lottery.vo.MasterFeedRateVo;
import com.prize.lottery.vo.MasterGladRateVo;
import com.prize.lottery.vo.dlt.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DltMasterMapper {

    int addDltMasterRates(List<DltMasterRatePo> rates);

    int addDltMasterRanks(List<DltMasterRankPo> ranks);

    int editDltVipMasters(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    int addDltHomeMasters(List<DltHomeMasterPo> masters);

    List<String> getUnRankedMasterPeriods();

    List<DltMasterRankPo> getDltMasterRankByPeriod(String period);

    int isDltVipMaster(@Param("masterId") String masterId, @Param("period") String period);

    String latestDltRatePeriod();

    String latestMasterRatePeriod(String masterId);

    int hasExistRatePeriod(String period);

    int hasExistRankPeriod(String period);

    String latestDltRankPeriod();

    List<DltMasterRatePo> getDltRankedMasterRates(@Param("period") String period,
                                                  @Param("type") String type,
                                                  @Param("limit") Integer limit);

    List<DltMasterRankPo> extractVipMasters(@Param("period") String period,
                                            @Param("types") List<String> types,
                                            @Param("limit") Integer limit,
                                            @Param("iLimit") Integer iLimit);

    List<DltICaiGladVo> getDltGladMasterList(PageCondition condition);

    int countDltGladMasterList(PageCondition condition);

    DltMasterDetail getDltMasterDetail(String masterId);

    List<DltMasterMulRankVo> getDltRandomMasters(@Param("period") String period,
                                                 @Param("last") String last,
                                                 @Param("ranks") List<Integer> ranks);

    List<DltMasterMulRankVo> getDltMasterMulRankList(PageCondition condition);

    int countDltMasterMulRanks(PageCondition condition);

    List<DltMasterRankVo> getDltMasterRankList(PageCondition condition);

    int countDltMasterRankList(PageCondition condition);

    List<LotteryMasterVo> getDltLottoMasterList(PageCondition condition);

    int countDltLottoMasters(PageCondition condition);

    List<DltMasterRatePo> getDltMasterRates(String period);

    DltMasterRatePo getMasterLatestRate(String masterId);

    List<HomeMasterVo> getDltHomeMasters(String period);

    String latestHomeMasterPeriod();

    int hasExtractHomeMaster(String period);

    int hasExtractVipMaster(String period);

    List<DltMasterSubscribeVo> getDltMasterSubscribes(PageCondition condition);

    int countDltMasterSubscribes(PageCondition condition);

    List<DltMasterSchemaVo> getDltSchemaMasters(@Param("period") String period, @Param("limit") Integer limit);

    DltMasterRateVo getDltMasterRateVo(@Param("period") String period, @Param("masterId") String masterId);

    MasterGladRateVo getDltR20BestMaster(String period);

    List<MasterGladRateVo> getDltRk3BestMasters(String period);

    List<MasterFeedRateVo> getDltMasterFeeds(String period);

    MasterFeedRateVo getDltMasterFeed(@Param("field") String field,
                                      @Param("period") String period,
                                      @Param("masterId") String masterId);

    List<String> getWorseMasters(WorseMasterFilter filter);

    int delMasterRanks(List<String> masterIds);

    int delMasterRates(List<String> masterIds);

}
