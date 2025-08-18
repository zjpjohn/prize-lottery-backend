package com.prize.lottery.application.query.service;


import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.vo.*;
import com.prize.lottery.enums.CodeType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.WensFilterResult;
import com.prize.lottery.po.lottery.*;
import com.prize.lottery.vo.Num3LottoFollowVo;
import com.prize.lottery.vo.pl5.Pl5ItemOmitVo;

import java.util.List;
import java.util.Map;

public interface ILotteryQueryService {

    String latestPeriod(LotteryEnum type);

    List<String> lotteryPeriods(LotteryEnum type, Integer limit);

    Page<LotteryInfoPo> getLotteryInfoByPage(LotteryListQuery query);

    Map<String, LotteryInfoPo> lotteryFastTable(String type, String period);

    LotteryInfoVo getLotteryDetail(String type, String period);

    List<LotteryInfoPo> getLatestGroupsLotteries();

    List<LotteryInfoPo> getLatestGroupsV1Lotteries();

    List<LotteryInfoPo> getTypedLatestLotteries(List<String> types);

    List<LotteryInfoPo> getLatestLimitLotteries(LotteryEnum type, Integer limit);

    LotteryInfoPo getLatestLottery(LotteryEnum lottery);

    List<LotteryOmitPo> getLotteryOmitList(LotteryEnum lottery, Integer size);

    List<LotterySumOmitPo> getSumOmitList(LotteryEnum type, Integer size);

    List<LotteryKuaOmitPo> getKuaOmitList(LotteryEnum type, Integer size);

    List<LotteryTrendOmitPo> getTrendOmitList(LotteryEnum type, Integer size);

    List<LotteryMatchOmitPo> getMatchOmitList(LotteryEnum type, Integer size);

    List<LotteryItemOmitPo> getItemOmitList(LotteryEnum type, Integer size);

    List<LotteryKl8OmitPo> getKl8OmitList();

    List<LotteryKl8OmitPo> getKl8TailOmits(Integer limit);

    Page<LotteryKl8OmitPo> getKl8BaseOmits(PageQuery query);

    Page<LotteryIndexPo> getLotteryIndexList(LotteryIndexQuery query);

    List<LotteryCodeVo> getLotteryCodeList(LotteryEnum lotto, CodeType type);

    Page<LotteryCodeVo> getLotteryCodeList(LotteryCodeQuery query);

    List<LotteryDanVo> getLotteryDanList(LotteryEnum type);

    List<LotteryOttVo> getLotteryOttList(LotteryEnum type, Integer size);

    Page<LotteryAroundPo> getAroundList(LottoAroundQuery query);

    Page<LotteryHoneyPo> getHoneyList(LottoHoneyQuery query);

    List<String> aroundPeriods(LotteryEnum type);

    List<String> honeyPeriods(LotteryEnum type);

    LotteryAroundVo lotteryAround(String period, LotteryEnum type);

    LotteryHoneyVo lotteryHoney(String period, LotteryEnum type);

    WensFilterResult filterNum3Lotto(WensFilterQuery query);

    Num3LotteryVo getNum3Lottery(LotteryEnum type, String period);

    Num3LotteryVo getNum3LastLottery(LotteryEnum type, String period);

    List<String> num3LotteryPeriods(LotteryEnum type);

    List<LotteryInfoPo> num3BeforeLimitLotteries(LotteryEnum type, String period, Integer limit);

    Page<Num3LottoIndexPo> getNum3IndexList(Num3IndexQuery query);

    Num3LottoDanVo getNum3LottoDan(LotteryEnum type, String period);

    List<LottoN3PianOmitVo> getN3PianOmits(LotteryEnum type, Integer limit);

    Page<LotteryFairTrialPo> fairTrialList(TrialListQuery query);

    List<String> trialPeriods(LotteryEnum type, Integer limit);

    Map<String, LotteryFairTrialPo> trailTable(LotteryEnum type, String period);

    Map<Integer, List<String>> getNum3ComCounts(LotteryEnum type, Integer limit);

    List<Num3LottoFollowVo> getNum3FollowList(LotteryEnum type, String period);

    List<Num3LottoFollowVo> getComFollowList(LotteryEnum type, String com);

    List<Num3LottoFollowVo> getFilterList(LottoFilterQuery query);

    List<LotteryPl5OmitPo> getPl5OmitList(Integer size);

    List<Pl5ItemOmitVo> getPl5ItemOmits(Integer type, Integer limit);

}
