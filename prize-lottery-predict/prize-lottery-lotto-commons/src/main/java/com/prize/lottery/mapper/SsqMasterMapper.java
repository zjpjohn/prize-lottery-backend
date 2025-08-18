package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.dto.WorseMasterFilter;
import com.prize.lottery.po.ssq.SsqHomeMasterPo;
import com.prize.lottery.po.ssq.SsqMasterRankPo;
import com.prize.lottery.po.ssq.SsqMasterRatePo;
import com.prize.lottery.vo.HomeMasterVo;
import com.prize.lottery.vo.LotteryMasterVo;
import com.prize.lottery.vo.MasterFeedRateVo;
import com.prize.lottery.vo.MasterGladRateVo;
import com.prize.lottery.vo.ssq.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsqMasterMapper {

    int addSsqMasterRates(List<SsqMasterRatePo> rates);

    int addSsqMasterRanks(List<SsqMasterRankPo> ranks);

    int editSsqVipMasters(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    int addSsqHomeMasters(List<SsqHomeMasterPo> masters);

    List<SsqMasterRankPo> getSsqMasterRankByPeriod(String period);

    int isSsqVipMaster(@Param("masterId") String masterId, @Param("period") String period);

    String latestSsqRatePeriod();

    String latestMasterRatePeriod(String masterId);

    List<String> getUnRankedMasterPeriods();

    String latestSsqRankPeriod();

    int hasExistRatePeriod(String period);

    SsqMasterRatePo getLatestMasterRate(String masterId);

    List<SsqMasterRatePo> getSsqRankedMasterRates(@Param("period") String period,
                                                  @Param("type") String type,
                                                  @Param("limit") Integer limit);

    List<SsqMasterRankPo> extractVipMasters(@Param("period") String period,
                                            @Param("types") List<String> types,
                                            @Param("limit") Integer limit,
                                            @Param("iLimit") Integer iLimit);

    List<SsqICaiGladVo> getSsqGladMasters(PageCondition condition);

    int countSsqGladMasters(PageCondition condition);

    SsqMasterDetail getSsqMasterDetail(String masterId);

    List<SsqMasterMulRankVo> getSsqRandomMasters(@Param("period") String period,
                                                 @Param("last") String last,
                                                 @Param("ranks") List<Integer> ranks);

    List<SsqMasterMulRankVo> getSsqMasterMulRankList(PageCondition condition);

    int countSsqMasterMulRanks(PageCondition condition);

    List<SsqMasterRankVo> getSsqMasterRankList(PageCondition condition);

    int countSsqMasterRankList(PageCondition condition);

    List<LotteryMasterVo> getSsqLottoMasterList(PageCondition condition);

    int countSsqLottoMasters(PageCondition condition);

    List<SsqMasterRatePo> getSsqMasterRates(String period);

    List<HomeMasterVo> getSsqHomeMasters(String period);

    String latestHomedMasterPeriod();

    int hasExtractHomeMaster(String period);

    int hasExtractVipMaster(String period);

    int hasExistRankMaster(String period);

    List<SsqMasterSubscribeVo> getSsqMasterSubScribeList(PageCondition condition);

    int countSsqMasterSubscribes(PageCondition condition);

    List<SsqMasterSchemaVo> getSsqSchemaMasters(@Param("period") String period, @Param("limit") Integer limit);

    SsqMasterRateVo getSsqMasterRateVo(@Param("period") String period, @Param("masterId") String masterId);

    MasterGladRateVo getSsqR20BestMaster(String period);

    List<MasterGladRateVo> getSsqRk3BestMasters(String period);

    List<MasterFeedRateVo> getSsqMasterFeeds(String period);

    MasterFeedRateVo getSsqMasterFeed(@Param("field") String field,
                                      @Param("period") String period,
                                      @Param("masterId") String masterId);

    List<String> getWorseMasters(WorseMasterFilter filter);

    int delMasterRanks(List<String> masterIds);

    int delMasterRates(List<String> masterIds);

}
