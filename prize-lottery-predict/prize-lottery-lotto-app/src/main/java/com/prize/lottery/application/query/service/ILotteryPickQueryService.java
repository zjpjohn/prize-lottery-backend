package com.prize.lottery.application.query.service;


import com.prize.lottery.application.query.dto.LotteryPickQuery;
import com.prize.lottery.application.vo.LotteryPickVo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;

public interface ILotteryPickQueryService {

    LotteryPickVo getLotteryPick(LotteryPickQuery query);

    List<String> indexPeriods(String lottery);

    List<String> num3IndexPeriods(LotteryEnum lottery);

}
