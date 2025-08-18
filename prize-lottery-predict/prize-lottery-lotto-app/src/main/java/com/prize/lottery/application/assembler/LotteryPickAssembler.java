package com.prize.lottery.application.assembler;

import com.prize.lottery.application.vo.LotteryPickVo;
import com.prize.lottery.po.lottery.LotteryPickPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LotteryPickAssembler {

    @Mapping(source = "gmtModify", target = "timestamp")
    @Mapping(source = "blueBall.balls", target = "blues")
    @Mapping(source = "redBall.balls", target = "reds")
    LotteryPickVo toVo(LotteryPickPo pick);

}
