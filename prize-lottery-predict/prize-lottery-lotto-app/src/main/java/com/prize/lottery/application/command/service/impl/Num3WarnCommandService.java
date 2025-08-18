package com.prize.lottery.application.command.service.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.executor.lotto.num3.Num3ComWarnBrowseExe;
import com.prize.lottery.application.command.service.INum3WarnCommandService;
import com.prize.lottery.application.query.executor.Num3WarnLayerBrowseExe;
import com.prize.lottery.application.vo.Num3ComWarnVo;
import com.prize.lottery.application.vo.Num3LayerVo;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.FeeDataResult;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Num3WarnCommandService implements INum3WarnCommandService {

    private final Num3ComWarnBrowseExe   comWarnBrowseExe;
    private final Num3WarnLayerBrowseExe warnLayerBrowseExe;

    @Override
    @Transactional
    public FeeDataResult<Num3ComWarnVo> num3ComWarn(Long userId, LotteryEnum type, String period) {
        Assert.state(type == LotteryEnum.FC3D || type == LotteryEnum.PL3, ResponseHandler.LOTTERY_TYPE_ERROR);
        return comWarnBrowseExe.execute(userId, type, period);
    }

    @Override
    public FeeDataResult<Num3LayerVo> num3Layer(Long userId, LotteryEnum type, String period) {
        Assert.state(type == LotteryEnum.FC3D || type == LotteryEnum.PL3, ResponseHandler.LOTTERY_TYPE_ERROR);
        return warnLayerBrowseExe.execute(userId, type, period);
    }
}
