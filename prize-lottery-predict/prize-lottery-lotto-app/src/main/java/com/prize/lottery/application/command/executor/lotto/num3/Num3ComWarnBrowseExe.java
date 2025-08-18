package com.prize.lottery.application.command.executor.lotto.num3;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.application.vo.Num3ComWarnVo;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.enums.BrowseType;
import com.prize.lottery.enums.HitType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.Num3ComWarnMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.lottery.Num3ComWarningPo;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Num3ComWarnBrowseExe {

    private final ForecastLottoBrowseExecutor browseExecutor;
    private final Num3ComWarnMapper           warnMapper;
    private final LotteryInfoMapper           lotteryMapper;

    public FeeDataResult<Num3ComWarnVo> execute(Long userId, LotteryEnum type, String period) {
        if (StringUtils.isBlank(period)) {
            period = warnMapper.latestWarnPeriod(type);
            Assert.state(StringUtils.isNotBlank(period), ResponseHandler.WARNING_NONE);
        }
        Num3ComWarningPo warning = warnMapper.getNum3ComWarnByUk(type, period)
                                             .orElseThrow(ResponseHandler.WARNING_NONE);

        String              lastPeriod = type.lastPeriod(period);
        List<LotteryInfoPo> lotteries  = lotteryMapper.getLotteryGtePeriodLimit2(type.getType(), lastPeriod);
        Assert.state(!CollectionUtils.isEmpty(lotteries), ResponseHandler.LOTTERY_INFO_ERROR);

        Integer       browses = browseExecutor.browses(type, BrowseType.NUM3_WARN);
        Num3ComWarnVo result  = new Num3ComWarnVo();
        result.setType(type);
        result.setPeriod(period);
        result.setBrowses(browses);
        result.setLastLottery(lotteries.get(0));
        warnMapper.getNum3ComWarnByUk(type, lastPeriod).map(Num3ComWarningPo::getHit).ifPresent(result::setLastHit);

        //未计算数据需进行账户余额校验
        if (warning.getCalcTime() == null) {
            Period         periodVal = new Period(period, 0, 0);
            ForecastBrowse browse    = new ForecastBrowse(userId, periodVal, type);
            browse.num3Warn();
            //用户会员信息校验
            Pair<Boolean, Num3ComWarningPo> pair = browseExecutor.executeOnlyMember(browse, () -> warning);
            if (!pair.getKey()) {
                return FeeDataResult.failure(period, result, null);
            }
        }
        result.setComWarning(warning);
        if (warning.getHit() != HitType.UN_OPEN) {
            result.setCurrentLottery(lotteries.get(1));
        }
        return FeeDataResult.success(result, period);
    }
}
