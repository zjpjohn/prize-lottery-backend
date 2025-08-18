package com.prize.lottery.application.command.executor.lotto.fsd;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.application.vo.N3WarnRecommendVo;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.enums.BrowseType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.WarningHit;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.Fc3dComRecommendMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.fc3d.Fc3dComRecommendPo;
import com.prize.lottery.po.fc3d.Fc3dEarlyWarningPo;
import com.prize.lottery.po.lottery.LotteryInfoPo;
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
public class FsdWarnRecommendBrowseExe {

    private final ForecastLottoBrowseExecutor browseExecutor;
    private final Fc3dComRecommendMapper      recommendMapper;
    private final LotteryInfoMapper           lotteryInfoMapper;

    /**
     * 查看系统号码预警及推荐
     *
     * @param userId 用户标识
     */
    public FeeDataResult<N3WarnRecommendVo> execute(Long userId, String period) {
        if (StringUtils.isBlank(period)) {
            period = recommendMapper.latestRecommendPeriod();
            Assert.state(StringUtils.isNotBlank(period), ResponseHandler.RECOMMEND_NONE);
        }
        //最新预测期号
        Fc3dComRecommendPo recommend = recommendMapper.getFc3dComRecommend(period);
        Assert.notNull(recommend, ResponseHandler.RECOMMEND_NONE);

        String              lastPeriod = LotteryEnum.FC3D.lastPeriod(period);
        List<LotteryInfoPo> lotteries  = lotteryInfoMapper.getLotteryGtePeriodLimit2(LotteryEnum.FC3D.getType(), lastPeriod);
        Assert.state(!CollectionUtils.isEmpty(lotteries), ResponseHandler.LOTTERY_INFO_ERROR);

        Integer           browses       = browseExecutor.browses(LotteryEnum.FC3D, BrowseType.LOTTO_WARN);
        N3WarnRecommendVo warnRecommend = new N3WarnRecommendVo();
        warnRecommend.setBrowses(browses);
        warnRecommend.setType(LotteryEnum.FC3D);
        warnRecommend.setPeriod(recommend.getPeriod());
        warnRecommend.setLastLottery(lotteries.get(0));
        //上期命中情况
        recommendMapper.getRecommendHit(lastPeriod)
                       .map(Fc3dComRecommendPo::getHit)
                       .map(WarningHit::of)
                       .ifPresent(warnRecommend::setLastHit);
        //未开奖计算需进行账户余额校验
        if (recommend.getCalcTime() == null) {
            Period         periodVal = new Period(period, 0, 0);
            ForecastBrowse browse    = new ForecastBrowse(userId, periodVal, LotteryEnum.FC3D);
            browse.lottoWarn();
            //用户会员信息校验
            Pair<Boolean, Fc3dComRecommendPo> pair = browseExecutor.executeOnlyMember(browse, () -> recommend);
            if (!pair.getKey()) {
                return FeeDataResult.failure(period, warnRecommend, null);
            }
        }
        //推荐预警信息
        List<Fc3dEarlyWarningPo> warnings = recommendMapper.getEarlyWarnings(period);
        warnRecommend.setWarnings(this.toMap(warnings));
        //本期推荐结果
        warnRecommend.setZu3(recommend.getZu3());
        warnRecommend.setZu6(recommend.getZu6());
        warnRecommend.setHit(recommend.getHit());
        //已开奖计算，返回本期开奖数据
        if (recommend.getHit() != null) {
            warnRecommend.setCurrentLottery(lotteries.get(1));
        }
        return FeeDataResult.success(warnRecommend, period);
    }

    private Map<String, List<String>> toMap(List<Fc3dEarlyWarningPo> warnings) {
        return warnings.stream()
                       .collect(Collectors.toMap(key -> key.getType().name(), value -> value.getWarn().getValues()));
    }

}
