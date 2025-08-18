package com.prize.lottery.application.command.executor.lotto.index;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.LotteryIndexCmd;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.application.vo.LotteryIndexVo;
import com.prize.lottery.application.vo.Num3LottoIndexVo;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.props.CloudForecastProperties;
import com.prize.lottery.mapper.LotteryIndexMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.Num3LottoIndexMapper;
import com.prize.lottery.po.lottery.LotteryIndexPo;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.lottery.Num3LottoIndexPo;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryIndexBrowseExe {

    private final CloudForecastProperties     properties;
    private final ForecastLottoBrowseExecutor browseExecutor;
    private final LotteryIndexMapper   indexMapper;
    private final LotteryInfoMapper    lotteryMapper;
    private final Num3LottoIndexMapper num3IndexMapper;

    public LotteryIndexVo execute(LotteryIndexCmd command) {
        LotteryEnum lottery = LotteryEnum.findOf(command.getLottery());
        String indexPeriod = Optional.ofNullable(command.getPeriod())
                                     .filter(StringUtils::isNotBlank)
                                     .orElseGet(() -> indexMapper.latestPeriod(command.getLottery()));
        Assert.state(StringUtils.isNotBlank(indexPeriod), ResponseHandler.INTELLECT_PERIOD_ERROR);

        String latestPeriod = lotteryMapper.latestPeriod(lottery.getType());
        Assert.notNull(latestPeriod, ResponseHandler.LOTTERY_INFO_ERROR);

        Period        period      = new Period(indexPeriod);
        LotteryInfoPo lotteryInfo = null;
        if (latestPeriod.compareTo(indexPeriod) >= 0) {
            lotteryInfo = lotteryMapper.getLotteryInfo(command.getLottery(), indexPeriod);
            period.calculated();
        }
        ForecastBrowse browse = new ForecastBrowse(command.getUserId(), period, lottery);
        browse.lotteryIndex(properties.getExpend());

        LotteryIndexPo lotteryIndex =
            this.browseExecutor.execute(browse, () -> indexMapper.getLotteryIndexByUk(lottery.getType(), period.getPeriod(), 2));

        LotteryIndexVo indexVo = new LotteryIndexVo();
        indexVo.setType(lottery);
        indexVo.setPeriod(indexPeriod);
        indexVo.setRedBall(lotteryIndex.getRedBall());
        indexVo.setBlueBall(lotteryIndex.getBlueBall());
        indexVo.setLotteryInfo(lotteryInfo);
        return indexVo;
    }

    public Num3LottoIndexVo num3Index(LotteryIndexCmd cmd) {
        LotteryEnum lottery = LotteryEnum.findOf(cmd.getLottery());
        String indexPeriod = Optional.ofNullable(cmd.getPeriod())
                                     .filter(StringUtils::isNotBlank)
                                     .orElseGet(() -> num3IndexMapper.latestIndexPeriod(lottery));
        Assert.state(StringUtils.isNotBlank(indexPeriod), ResponseHandler.INTELLECT_PERIOD_ERROR);

        String latestPeriod = lotteryMapper.latestPeriod(lottery.getType());
        Assert.notNull(latestPeriod, ResponseHandler.LOTTERY_INFO_ERROR);

        Period        period      = new Period(indexPeriod);
        LotteryInfoPo lotteryInfo = null;
        if (latestPeriod.compareTo(indexPeriod) >= 0) {
            lotteryInfo = lotteryMapper.getLotteryInfo(cmd.getLottery(), indexPeriod);
            period.calculated();
        }
        ForecastBrowse browse = new ForecastBrowse(cmd.getUserId(), period, lottery);
        browse.num3Index(properties.getExpend());

        Num3LottoIndexPo lottoIndex =
            this.browseExecutor.execute(browse, () -> num3IndexMapper.getLottoIndexByUk(lottery, period.getPeriod()));

        Num3LottoIndexVo index = new Num3LottoIndexVo();
        index.setType(lottery);
        index.setPeriod(period.getPeriod());
        index.setLotteryInfo(lotteryInfo);
        index.setDanIndex(lottoIndex.getDanIndex());
        index.setComIndex(lottoIndex.getComIndex());
        index.setKillIndex(lottoIndex.getKillIndex());
        return index;
    }

}
