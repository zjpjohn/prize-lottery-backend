package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.dto.WorseMasterFilter;
import com.prize.lottery.po.pl3.Pl3HomeMasterPo;
import com.prize.lottery.po.pl3.Pl3MasterRankPo;
import com.prize.lottery.po.pl3.Pl3MasterRatePo;
import com.prize.lottery.vo.HomeMasterVo;
import com.prize.lottery.vo.LotteryMasterVo;
import com.prize.lottery.vo.MasterFeedRateVo;
import com.prize.lottery.vo.MasterGladRateVo;
import com.prize.lottery.vo.pl3.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Pl3MasterMapper {

    int addPl3MasterRates(List<Pl3MasterRatePo> rates);

    int addPl3MasterRanks(List<Pl3MasterRankPo> ranks);

    int editPl3VipMasters(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    int addPl3HomeMasters(List<Pl3HomeMasterPo> masters);

    String latestPl3RatePeriod();

    String latestMasterRatePeriod(String masterId);

    List<String> getUnRankedMasterPeriods();

    String latestPl3RankPeriod();

    int hasExistRatePeriod(String period);

    List<Pl3MasterRankPo> getPl3MasterRankByPeriod(String period);

    Pl3MasterRatePo getLatestMasterRate(String masterId);

    List<Pl3MasterRatePo> getPl3RankedMasterRates(@Param("period") String period,
                                                  @Param("type") String type,
                                                  @Param("limit") Integer limit);

    List<Pl3MasterRankPo> extractVipMasters(@Param("period") String period,
                                            @Param("types") List<String> types,
                                            @Param("limit") Integer limit,
                                            @Param("iLimit") Integer iLimit);

    int hasExistRankPeriod(String period);

    int hasExtractVipPeriod(String period);

    int isPl3VipMaster(@Param("masterId") String masterId, @Param("period") String period);

    List<Pl3ICaiGladVo> getPl3GladMasters(PageCondition condition);

    int countPl3GladMasters(PageCondition condition);

    Pl3MasterDetail getPl3MasterDetail(String masterId);

    List<Pl3MasterMulRankVo> getPl3RandomMasters(@Param("period") String period,
                                                 @Param("last") String last,
                                                 @Param("ranks") List<Integer> ranks);

    List<Pl3MasterMulRankVo> getPl3MasterMulRankList(PageCondition condition);

    int countPl3MasterMulRanks(PageCondition condition);

    List<Pl3MasterRankVo> getPl3MasterRankList(PageCondition condition);

    int countPl3MasterRanks(PageCondition condition);

    List<LotteryMasterVo> getLotteryMasterByIds(@Param("period") String period,
                                                @Param("masterIds") List<String> masterIds);

    List<LotteryMasterVo> getPl3LottoMasterList(PageCondition condition);

    int countPl3LottoMasters(PageCondition condition);

    List<Pl3MasterRatePo> getPl3MasterRates(String period);

    List<HomeMasterVo> getPl3HomeMasters(String period);

    int hasExtractHomeMaster(String period);

    String latestHomedMasterPeriod();

    List<Pl3MasterSubscribeVo> getPl3MasterSubscribeList(PageCondition condition);

    int countPl3MasterSubscribes(PageCondition condition);

    List<Pl3MasterSchemaVo> getPl3SchemaMasters(@Param("period") String period, @Param("limit") Integer limit);

    Pl3MasterRateVo getPl3MasterRateVo(@Param("period") String period, @Param("masterId") String masterId);

    MasterGladRateVo getPl3D3HitBestMaster(String period);

    List<MasterGladRateVo> getPl3C7BestMasters(String period);

    List<MasterFeedRateVo> getPl3MasterFeeds(String period);

    MasterFeedRateVo getPl3MasterFeed(@Param("field") String field,
                                      @Param("period") String period,
                                      @Param("masterId") String masterId);

    List<String> getWorseMasters(WorseMasterFilter filter);

    int delMasterRanks(List<String> masterIds);

    int delMasterRates(List<String> masterIds);

}
