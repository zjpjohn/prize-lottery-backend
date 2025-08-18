package com.prize.lottery.application.command.executor.lotto.pls;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.LotteryAssembler;
import com.prize.lottery.application.command.executor.lotto.ForecastLottoBrowseExecutor;
import com.prize.lottery.application.vo.N3TodayPivotVo;
import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.enums.BrowseType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.Pl3MasterMapper;
import com.prize.lottery.mapper.Pl3PivotMapper;
import com.prize.lottery.po.pl3.Pl3PivotPo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.LotteryMasterVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlsTodayPivotBrowseExe {

    private final Pl3PivotMapper              pivotMapper;
    private final Pl3MasterMapper             masterMapper;
    private final ForecastLottoBrowseExecutor browseExecutor;
    private final LotteryAssembler            lotteryAssembler;

    public FeeDataResult<N3TodayPivotVo> execute(Long userId, String period) {
        if (StringUtils.isBlank(period)) {
            period = pivotMapper.latestPivotPeriod();
            Assert.state(StringUtils.isNotBlank(period), ResponseHandler.PIVOT_NONE);
        }
        Pl3PivotPo pivot = pivotMapper.getPl3Pivot(period).orElseThrow(Assert.supply(ResponseHandler.PIVOT_NONE));
        if (pivot.getCalcTime() == null) {
            Period         periodVal = new Period(period, 0, 0);
            ForecastBrowse browse    = new ForecastBrowse(userId, periodVal, LotteryEnum.PL3);
            browse.lottoPivot(pivot.getId().toString());
            Pair<Boolean, Pl3PivotPo> executed = browseExecutor.executeOnlyMember(browse, () -> pivot);
            if (!executed.getKey()) {
                return FeeDataResult.failure(period);
            }
        }
        String                lastPeriod = LotteryEnum.PL3.lastPeriod(period);
        List<LotteryMasterVo> masterList = masterMapper.getLotteryMasterByIds(lastPeriod, pivot.masterIds());
        List<N3TodayPivotVo.PivotMaster> pivotMasters = masterList.stream()
                                                                  .map(N3TodayPivotVo.PivotMaster::new)
                                                                  .collect(Collectors.toList());

        N3TodayPivotVo todayPivot = lotteryAssembler.toVo(pivot);
        todayPivot.setMasters(pivotMasters);
        Integer browses = browseExecutor.browses(LotteryEnum.PL3, BrowseType.TODAY_PIVOT);
        todayPivot.setBrowse(browses);
        return FeeDataResult.success(todayPivot, period);
    }

}
