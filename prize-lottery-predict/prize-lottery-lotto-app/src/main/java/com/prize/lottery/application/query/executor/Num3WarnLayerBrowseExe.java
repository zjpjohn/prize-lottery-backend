package com.prize.lottery.application.query.executor;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.application.vo.Num3LayerVo;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.enums.BrowseType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.Num3LayerMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.lottery.Num3LayerFilterPo;
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
public class Num3WarnLayerBrowseExe {

    private final ForecastLottoBrowseExecutor browseExecutor;
    private final Num3LayerMapper             layerMapper;
    private final LotteryInfoMapper           lotteryMapper;

    public FeeDataResult<Num3LayerVo> execute(Long userId, LotteryEnum type, String period) {
        if (StringUtils.isBlank(period)) {
            period = layerMapper.latestPeriod(type);
            Assert.state(StringUtils.isNotBlank(period), ResponseHandler.WARNING_NONE);
        }
        Num3LayerFilterPo   layer      = layerMapper.getNum3LayerFilterByUk(period, type);
        String              lastPeriod = type.lastPeriod(period);
        List<LotteryInfoPo> lotteries  = lotteryMapper.getLotteryGtePeriodLimit2(type.getType(), lastPeriod);
        Assert.state(!CollectionUtils.isEmpty(lotteries), ResponseHandler.LOTTERY_INFO_ERROR);
        Integer     browses = browseExecutor.browses(type, BrowseType.NUM3_LAYER);
        Num3LayerVo result  = new Num3LayerVo();
        result.setType(type);
        result.setPeriod(period);
        result.setBrowses(browses);
        result.setLastLottery(lotteries.get(0));

        if (layer.getCalcTime() != null) {
            result.setCurrentLottery(lotteries.get(1));
        } else {
            //未计算数据需进行账户余额校验
            Period         periodVal = new Period(period, 0, 0);
            ForecastBrowse browse    = new ForecastBrowse(userId, periodVal, type);
            browse.num3Layer();
            //用户会员信息校验
            Pair<Boolean, Num3LayerFilterPo> pair = browseExecutor.executeOnlyMember(browse, () -> layer);
            if (!pair.getKey()) {
                return FeeDataResult.failure(period, result, null);
            }
        }
        result.setLayer(layer);
        return FeeDataResult.success(result, period);
    }
}
