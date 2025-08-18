package com.prize.lottery.application.command.service;


import com.prize.lottery.application.command.dto.LotteryIndexCmd;
import com.prize.lottery.application.command.dto.LottoN3PickCmd;
import com.prize.lottery.application.command.dto.LottoPickCmd;
import com.prize.lottery.application.vo.LotteryIndexVo;
import com.prize.lottery.application.vo.Num3LottoIndexVo;

public interface ILotteryPickCommandService {

    void lottoPick(Long userId, LottoN3PickCmd command);

    void lottoPick(Long userId, LottoPickCmd command);

    LotteryIndexVo lotteryIndex(LotteryIndexCmd command);

    Num3LottoIndexVo num3Index(LotteryIndexCmd command);

}
