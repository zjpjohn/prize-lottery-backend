package com.prize.lottery.mapper;

import com.prize.lottery.po.lottery.LotteryPickPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LotteryPickMapper {

    int addLotteryPick(LotteryPickPo pick);

    LotteryPickPo getLotteryPickByUk(@Param("lottery") String lottery,
                                     @Param("period") String period,
                                     @Param("userId") Long userId);

}
