package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.lottery.model.LotteryAroundDo;
import com.prize.lottery.domain.lottery.model.LotteryHoneyDo;
import com.prize.lottery.po.lottery.LotteryAroundPo;
import com.prize.lottery.po.lottery.LotteryHoneyPo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LotteryDanConverter {

    LotteryAroundDo toDo(LotteryAroundPo around);

    LotteryAroundPo toPo(LotteryAroundDo around);

    List<LotteryAroundPo> toAroundList(List<LotteryAroundDo> aroundList);

    LotteryHoneyDo toDo(LotteryHoneyPo honey);

    LotteryHoneyPo toPo(LotteryHoneyDo honey);

    List<LotteryHoneyPo> toHoneyList(List<LotteryHoneyDo> honeyList);
}
