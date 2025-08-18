package com.prize.lottery.domain.lottery.assembler;

import com.prize.lottery.domain.lottery.model.LotteryInfoDo;
import com.prize.lottery.domain.lottery.model.LotteryTrialDo;
import com.prize.lottery.domain.lottery.value.LevelValue;
import com.prize.lottery.po.lottery.LotteryAwardPo;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.lottery.LotteryLevelPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LotteryInfoAssembler {

    @Mapping(source = "lottery.type", target = "type")
    LotteryInfoPo toLottery(LotteryInfoDo lottery);

    List<LotteryInfoPo> toLotteries(List<LotteryInfoDo> lotteries);

    @Mapping(source = "lottery.type", target = "type")
    LotteryAwardPo toAward(LotteryInfoDo lottery);

    List<LotteryAwardPo> toAwards(List<LotteryInfoDo> lotteries);

    @Mapping(source = "lottery.lottery.type", target = "type")
    @Mapping(source = "lottery.period", target = "period")
    LotteryLevelPo toLevel(LotteryInfoDo lottery, LevelValue level);

    @Mapping(source = "lottery.type", target = "type")
    LotteryInfoPo toTrial(LotteryTrialDo trial);

    List<LotteryInfoPo> toTrials(List<LotteryTrialDo> trials);

}
