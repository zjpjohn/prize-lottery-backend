package com.prize.lottery.application.command.service;


import com.prize.lottery.application.vo.Num3ComWarnVo;
import com.prize.lottery.application.vo.Num3LayerVo;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.FeeDataResult;

public interface INum3WarnCommandService {

    FeeDataResult<Num3ComWarnVo> num3ComWarn(Long userId, LotteryEnum type, String period);

    FeeDataResult<Num3LayerVo> num3Layer(Long userId, LotteryEnum type, String period);

}
