package com.prize.lottery.application.command.executor.lotto.pls;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.application.vo.N3WarnRecommendVo;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.enums.BrowseType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.WarningHit;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.Pl3ComRecommendMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.pl3.Pl3ComRecommendPo;
import com.prize.lottery.po.pl3.Pl3EarlyWarningPo;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlsWarnRecommendBrowseExe {

    private final ForecastLottoBrowseExecutor browseExecutor;
    private final Pl3ComRecommendMapper       recommendMapper;
    private final LotteryInfoMapper           lotteryInfoMapper;

    /**
     * 查看排列三系统号码预警及推荐
     *
     * @param userId 用户标识
     */
    public FeeDataResult<N3WarnRecommendVo> execute(Long userId, String period) {
        if (StringUtils.isBlank(period)) {
            period = recommendMapper.latestRecommendPeriod();
            Assert.state(StringUtils.isNotBlank(period), ResponseHandler.RECOMMEND_NONE);
        }
        //最新期预警信息
        Pl3ComRecommendPo recommend = recommendMapper.getComRecommend(period);
        Assert.notNull(recommend, ResponseHandler.RECOMMEND_NONE);

        //排列三开奖数据
        String              lastPeriod = LotteryEnum.PL3.lastPeriod(period);
        List<LotteryInfoPo> lotteries  = lotteryInfoMapper.getLotteryGtePeriodLimit2(LotteryEnum.PL3.getType(), lastPeriod);
        Assert.state(!CollectionUtils.isEmpty(lotteries), ResponseHandler.LOTTERY_INFO_ERROR);

        Integer           browses       = browseExecutor.browses(LotteryEnum.PL3, BrowseType.LOTTO_WARN);
        N3WarnRecommendVo warnRecommend = new N3WarnRecommendVo();
        warnRecommend.setBrowses(browses);
        warnRecommend.setType(LotteryEnum.PL3);
        warnRecommend.setPeriod(recommend.getPeriod());
        warnRecommend.setLastLottery(lotteries.get(0));
        //上期命中情况
        recommendMapper.getRecommendHit(lastPeriod)
                       .map(Pl3ComRecommendPo::getHit)
                       .map(WarningHit::of)
                       .ifPresent(warnRecommend::setLastHit);
        //未开奖计算需进行账户余额校验
        if (recommend.getCalcTime() == null) {
            Period         periodVal = new Period(period, 0, 0);
            ForecastBrowse browse    = new ForecastBrowse(userId, periodVal, LotteryEnum.PL3);
            browse.lottoWarn();
            //用户会员信息校验
            Pair<Boolean, Pl3ComRecommendPo> pair = browseExecutor.executeOnlyMember(browse, () -> recommend);
            if (!pair.getKey()) {
                return FeeDataResult.failure(period, warnRecommend, null);
            }
        }
        //预警信息
        List<Pl3EarlyWarningPo> warnings = recommendMapper.getEarlyWarnings(period);
        warnRecommend.setWarnings(this.toMap(warnings));
        //推荐结果
        warnRecommend.setZu3(recommend.getZu3());
        warnRecommend.setZu6(recommend.getZu6());
        warnRecommend.setHit(recommend.getHit());
        //已开奖计算，返回本期开奖数据
        if (recommend.getHit() != null) {
            warnRecommend.setCurrentLottery(lotteries.get(1));
        }
        return FeeDataResult.success(warnRecommend, period);
    }

    private Map<String, List<String>> toMap(List<Pl3EarlyWarningPo> warnings) {
        return warnings.stream()
                       .collect(Collectors.toMap(key -> key.getType().name(), value -> value.getWarn().getValues()));
    }

}
