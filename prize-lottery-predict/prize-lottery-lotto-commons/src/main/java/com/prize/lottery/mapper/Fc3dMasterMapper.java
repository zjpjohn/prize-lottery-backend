package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.dto.WorseMasterFilter;
import com.prize.lottery.po.fc3d.Fc3dHomeMasterPo;
import com.prize.lottery.po.fc3d.Fc3dMasterRankPo;
import com.prize.lottery.po.fc3d.Fc3dMasterRatePo;
import com.prize.lottery.vo.HomeMasterVo;
import com.prize.lottery.vo.LotteryMasterVo;
import com.prize.lottery.vo.MasterFeedRateVo;
import com.prize.lottery.vo.MasterGladRateVo;
import com.prize.lottery.vo.fc3d.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Fc3dMasterMapper {

    int addFc3dMasterRates(List<Fc3dMasterRatePo> masterRates);

    int addFc3dMasterRanks(List<Fc3dMasterRankPo> masterRanks);

    int editFc3dVipMasters(@Param("period") String period, @Param("masterIds") List<String> masterIds);

    int addFc3dHomeMasters(List<Fc3dHomeMasterPo> homeMasters);

    String latestFc3dHomeMasterPeriod();

    int hasExtractHomeMaster(String period);

    List<Fc3dMasterRankPo> getFc3dMasterRankByPeriod(String period);

    String latestFc3dRatePeriod();

    String latestMasterRatePeriod(String masterId);

    String latestFc3dRankPeriod();

    List<String> getUnRankedMasterPeriods();

    Fc3dMasterRatePo getLatestMasterRate(String masterId);

    List<Fc3dMasterRatePo> getFc3dRankedMasterRates(@Param("period") String period,
                                                    @Param("type") String type,
                                                    @Param("limit") Integer limit);

    int hasExtractVipPeriod(String period);

    List<Fc3dMasterRatePo> getFc3dVipMasterByPeriod(@Param("period") String period, @Param("limit") Integer limit);

    List<Fc3dMasterRankPo> extractVipMasters(@Param("period") String period,
                                             @Param("types") List<String> types,
                                             @Param("limit") Integer limit,
                                             @Param("iLimit") Integer iLimit);

    int isFc3dVipMaster(@Param("masterId") String masterId, @Param("period") String period);

    List<Fc3dICaiGladVo> getFc3dGladMasters(PageCondition condition);

    int countFc3dGladMasters(PageCondition condition);

    Fc3dMasterDetail getFc3dMasterDetail(String masterId);

    List<Fc3dMasterMulRankVo> getFc3dRandomMasters(@Param("period") String period,
                                                   @Param("last") String last,
                                                   @Param("ranks") List<Integer> ranks);

    List<Fc3dMasterMulRankVo> getFc3dMasterMulRankList(PageCondition condition);

    int countFc3dMasterMulRanks(PageCondition condition);

    List<Fc3dMasterRankVo> getFc3dMasterRankList(PageCondition condition);

    int countFc3dMasterRanks(PageCondition condition);

    List<LotteryMasterVo> getLotteryMasterByIds(@Param("period") String period,
                                                @Param("masterIds") List<String> masterIds);

    List<LotteryMasterVo> getFc3dLottoMasterList(PageCondition condition);

    int countFc3dLottoMasters(PageCondition condition);

    int hasExistRatePeriod(String period);

    int hasExistRankPeriod(String period);

    List<Fc3dMasterRatePo> getFc3dMasterRates(String period);

    List<HomeMasterVo> getFc3dHomeMasters(String period);

    String latestHomedMasterPeriod();

    List<Fc3dMasterSubscribeVo> getFc3dMasterSubscribeList(PageCondition condition);

    int countFc3dMasterSubscribes(PageCondition condition);

    List<Fc3dMasterSchemaVo> getFc3dSchemaMasters(@Param("period") String period, @Param("limit") Integer limit);

    Fc3dMasterRateVo getFc3dMasterRateVo(@Param("period") String period, @Param("masterId") String masterId);

    MasterGladRateVo getFc3dD3HitBestMaster(String period);

    List<MasterGladRateVo> getFc3dC7BestMasters(String period);

    List<MasterFeedRateVo> getFc3dMasterFeeds(String period);

    MasterFeedRateVo getFc3dMasterFeed(@Param("field") String field,
                                       @Param("period") String period,
                                       @Param("masterId") String masterId);

    List<String> getWorseMasters(WorseMasterFilter filter);

    int delMasterRanks(List<String> masterIds);

    int delMasterRates(List<String> masterIds);

}
