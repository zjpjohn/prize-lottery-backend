package com.prize.lottery.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.enums.CodeType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.*;
import com.prize.lottery.vo.Num3ComCountVo;
import com.prize.lottery.vo.Num3LottoFollowVo;
import com.prize.lottery.vo.pl5.Pl5ItemOmitVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotteryInfoMapper {

    int addLotteryInfos(List<LotteryInfoPo> lotteries);

    int editN3Lotteries(List<LotteryInfoPo> lotteries);

    int addLotteryAwards(List<LotteryAwardPo> awards);

    int addLotteryLevels(List<LotteryLevelPo> levels);

    int addLotteryTrial(LotteryInfoPo lottery);

    int addLotteryTrials(List<LotteryInfoPo> trials);

    LotteryInfoPo getLotteryInfoById(Long id);

    LotteryInfoPo getNewestLottery(String type);

    List<LotteryInfoPo> getLatestLimitLotteries(@Param("type") String type, @Param("limit") Integer limit);

    List<LotteryInfoPo> getBeforeLimitLotteries(@Param("type") String type,
                                                @Param("period") String period,
                                                @Param("limit") Integer limit);

    String num3LatestPeriod(@Param("type") LotteryEnum type);

    List<String> num3LotteryPeriods(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

    int existLottery(@Param("type") String type, @Param("period") String period);

    List<LotteryInfoPo> getLotteryList(PageCondition condition);

    int countLotteryList(PageCondition condition);

    List<String> getLotteryPeriods(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

    LotteryInfoPo getLotteryByUk(@Param("type") String type, @Param("period") String period);

    LotteryInfoPo getLotteryInfo(@Param("type") String type, @Param("period") String period);

    List<LotteryInfoPo> getLotteriesByPeriods(@Param("type") LotteryEnum type, @Param("periods") List<String> periods);

    List<LotteryInfoPo> getLotteriesBetweenPeriods(@Param("type") LotteryEnum type,
                                                   @Param("start") String start,
                                                   @Param("end") String end);

    String latestPeriod(String type);

    LotteryAwardPo getLotteryAwardByUk(@Param("type") String type, @Param("period") String period);

    LotteryLevelPo getLotteryLevelById(Long id);

    List<LotteryLevelPo> getLotteryLevels(@Param("type") String type, @Param("period") String period);

    List<LotteryInfoPo> getNewestGroupLotteries();

    List<LotteryInfoPo> getLimitLotteryList(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

    List<LotteryInfoPo> getLotteryInfoGtPeriod(@Param("type") String type, @Param("period") String period);

    List<LotteryInfoPo> getLotteryGtePeriodLimit2(@Param("type") String type, @Param("period") String period);

    List<LotteryInfoPo> getNewestLotteriesByTypes(@Param("types") List<String> types);

    LotteryInfoPo getN3LastSameLottery(@Param("type") LotteryEnum type,
                                       @Param("com") String com,
                                       @Param("period") String period);

    int addLotteryOmits(List<LotteryOmitPo> omitList);

    int addLotteryOmitInfo(LotteryOmitPo omit);

    int hasLotteryOmit();

    LotteryOmitPo getLatestLotteryOmit(String type);

    LotteryOmitPo getInitLotteryOmit(String type);

    LotteryOmitPo getLotteryOmitInfo(@Param("type") String type, @Param("period") String period);

    List<LotteryOmitPo> getLotteryOmitList(PageCondition condition);

    List<LotteryOmitPo> getLotteryOmits(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

    List<LotteryOmitPo> getLotteryOmitsLtPeriod(@Param("type") LotteryEnum type,
                                                @Param("period") String period,
                                                @Param("limit") Integer limit);

    int countLotteryOmits(PageCondition condition);

    int addKl8Omits(List<LotteryKl8OmitPo> omitsList);

    LotteryKl8OmitPo getLatestKl8Omit();

    LotteryKl8OmitPo getKl8InitOmit();

    LotteryKl8OmitPo getLottoKl8Omit(String period);

    List<LotteryKl8OmitPo> getKl8BaseOmitList(PageCondition condition);

    int countKl8Omits(PageCondition condition);

    List<LotteryKl8OmitPo> getKl8OmitList(Integer limit);

    List<LotteryKl8OmitPo> getKl8TailOmits(Integer limit);

    int addSumOmits(List<LotterySumOmitPo> omits);

    List<LotterySumOmitPo> getSumOmitList(@Param("type") String type, @Param("limit") Integer limit);

    LotterySumOmitPo getLottoSumOmit(@Param("type") String type, @Param("period") String period);

    int addKuaOmits(List<LotteryKuaOmitPo> omits);

    LotteryKuaOmitPo getLottoKuaOmit(@Param("type") String type, @Param("period") String period);

    List<LotteryKuaOmitPo> getKuaOmitList(@Param("type") String type, @Param("limit") Integer limit);

    int addLotteryCodes(List<LotteryCodePo> codes);

    List<LotteryCodePo> getLottoCodesByType(@Param("lotto") LotteryEnum lotto,
                                            @Param("type") CodeType type,
                                            @Param("limit") Integer limit);

    LotteryCodePo getLottoCodeByUk(@Param("lotto") LotteryEnum lotto,
                                   @Param("period") String period,
                                   @Param("type") CodeType type);

    List<LotteryCodePo> getLotteryCodeList(PageCondition condition);

    int countLotteryCodes(PageCondition condition);

    int addLotteryDan(List<LotteryDanPo> dans);

    LotteryDanPo getLotteryDan(@Param("type") LotteryEnum type, @Param("period") String period);

    LotteryDanPo getLatestLotteryDan(@Param("type") LotteryEnum type);

    List<LotteryDanPo> getLotteryDanList(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

    int addLotteryOtt(List<LotteryOttPo> otts);

    LotteryOttPo getLotteryOtt(@Param("type") LotteryEnum type, @Param("period") String period);

    List<LotteryOttPo> getLotteryOttList(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

    int addPianOmits(List<LottoPianOmitPo> omits);

    List<LottoPianOmitPo> getLottoPianList(@Param("type") LotteryEnum type,
                                           @Param("level") Integer level,
                                           @Param("limit") Integer limit);

    int hasExistPianOmit(@Param("type") LotteryEnum type);

    int addLottoFairTrials(List<LotteryFairTrialPo> lotteries);

    LotteryFairTrialPo getLottoFailTrial(Long id);

    LotteryFairTrialPo getLottoFairTrialByUk(@Param("type") LotteryEnum type, @Param("period") String period);

    List<String> getFairTrialPeriods(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

    List<LotteryFairTrialPo> getFairTrialList(PageCondition condition);

    int countFairTrials(PageCondition condition);

    List<LotteryFairTrialPo> getFairTrialLtePeriod(@Param("type") LotteryEnum type, @Param("period") String period);

    String getTrialLatestPeriod(@Param("type") LotteryEnum type);

    List<Num3ComCountVo> getNum3ComCounts(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

    List<Num3LottoFollowVo> getFilterNum3Follows(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

    List<Num3LottoFollowVo> getNum3FollowList(@Param("type") LotteryEnum type,
                                              @Param("period") String period,
                                              @Param("com") String com);

    int addLotteryTrendOmits(List<LotteryTrendOmitPo> omits);

    LotteryTrendOmitPo getLatestTrendOmit(@Param("type") LotteryEnum type);

    LotteryTrendOmitPo getLotteryTrendOmit(@Param("type") LotteryEnum type, @Param("period") String period);

    List<LotteryTrendOmitPo> getLotteryTrendOmits(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

    int addLotteryMatchOmits(List<LotteryMatchOmitPo> omits);

    LotteryMatchOmitPo getLatestMatchOmit(@Param("type") LotteryEnum type);

    LotteryMatchOmitPo getLotteryMatchOmit(@Param("type") LotteryEnum type, @Param("period") String period);

    List<LotteryMatchOmitPo> getLotteryMatchOmits(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

    int addLotteryItemOmits(List<LotteryItemOmitPo> omits);

    LotteryItemOmitPo getLatestLotteryItemOmit(@Param("type") LotteryEnum type);

    LotteryItemOmitPo getLotteryItemOmit(@Param("type") LotteryEnum type, @Param("period") String period);

    List<LotteryItemOmitPo> getLotteryItemOmits(@Param("type") LotteryEnum type, @Param("limit") Integer limit);

    int addLotteryPl5Omits(List<LotteryPl5OmitPo> omits);

    LotteryPl5OmitPo getLatestLotteryPl5Omit();

    List<LotteryPl5OmitPo> getLotteryPl5Omits(Integer limit);

    LotteryPl5OmitPo getLotteryPl5Omit(@Param("period") String period);

    List<Pl5ItemOmitVo> getPl5ItemOmits(@Param("field") String field, @Param("limit") Integer limit);

}
