package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.pick.model.LotteryPickDo;
import com.prize.lottery.po.lottery.LotteryPickPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LotteryPickConverter {

    LotteryPickPo toPo(LotteryPickDo pick);

    LotteryPickDo toDo(LotteryPickPo pick);

}
