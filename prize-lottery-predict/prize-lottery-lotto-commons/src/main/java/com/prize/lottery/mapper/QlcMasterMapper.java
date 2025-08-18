package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.dto.WorseMasterFilter;
import com.prize.lottery.po.qlc.QlcHomeMasterPo;
import com.prize.lottery.po.qlc.QlcMasterRankPo;
import com.prize.lottery.po.qlc.QlcMasterRatePo;
import com.prize.lottery.vo.HomeMasterVo;
import com.prize.lottery.vo.LotteryMasterVo;
import com.prize.lottery.vo.MasterFeedRateVo;
import com.prize.lottery.vo.qlc.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QlcMasterMapper {

    int addQlcMasterRates(List<QlcMasterRatePo> rates);

    int addQlcMasterRanks(List<QlcMasterRankPo> ranks);

    int editQlcVipMasters(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    int addQlcHomeMasters(List<QlcHomeMasterPo> homeMasters);

    List<QlcMasterRankPo> getQlcMasterRankByPeriod(String period);

    int isQlcVipMaster(@Param("masterId") String masterId, @Param("period") String period);

    String latestQlcRatePeriod();

    String latestMasterRatePeriod(String masterId);

    String latestQlcRankPeriod();

    List<String> getUnRankedMasterPeriods();

    QlcMasterRatePo getLatestMasterRate(String masterId);

    List<QlcMasterRatePo> getQlcRankedMasterRates(@Param("period") String period,
                                                  @Param("type") String type,
                                                  @Param("limit") Integer limit);

    List<QlcMasterRankPo> extractVipMasters(@Param("period") String period,
                                            @Param("types") List<String> types,
                                            @Param("limit") Integer limit,
                                            @Param("iLimit") Integer iLimit);

    List<QlcICaiGladVo> getQlcGladMasters(PageCondition condition);

    int countQlcGladMasters(PageCondition condition);

    QlcMasterDetail getQlcMasterDetail(String masterId);

    List<QlcMasterMulRankVo> getQlcRandomMasters(@Param("period") String period,
                                                 @Param("last") String last,
                                                 @Param("ranks") List<Integer> ranks);

    List<QlcMasterMulRankVo> getQlcMasterMulRankList(PageCondition condition);

    int countQlcMasterMulRanks(PageCondition condition);

    List<QlcMasterRankVo> getQlcMasterRankList(PageCondition condition);

    int countQlcMasterRankList(PageCondition condition);

    List<LotteryMasterVo> getQlcLottoMasterList(PageCondition condition);

    int countQlcLottoMasters(PageCondition condition);

    int hasExistRatePeriod(String period);

    List<QlcMasterRatePo> getQlcMasterRates(String period);

    List<HomeMasterVo> getQlcHomeMasters(String period);

    String latestHomedMasterPeriod();

    int hasExtractHomeMaster(String period);

    int hasExtractVipMaster(String period);

    int hasExistRankPeriod(String period);

    List<QlcMasterSubscribeVo> getQlcMasterSubscribeList(PageCondition condition);

    int countQlcMasterSubscribeList(PageCondition condition);

    List<QlcMasterSchemaVo> getQlcSchemaMasters(@Param("period") String period, @Param("limit") Integer limit);

    QlcMasterRateVo getQlcMasterRateVo(@Param("period") String period, @Param("masterId") String masterId);

    List<MasterFeedRateVo> getQlcMasterFeeds(String period);

    MasterFeedRateVo getQlcMasterFeed(@Param("field") String field,
                                      @Param("period") String period,
                                      @Param("masterId") String masterId);

    List<String> getWorseMasters(WorseMasterFilter filter);

    int delMasterRanks(List<String> masterIds);

    int delMasterRates(List<String> masterIds);

}
