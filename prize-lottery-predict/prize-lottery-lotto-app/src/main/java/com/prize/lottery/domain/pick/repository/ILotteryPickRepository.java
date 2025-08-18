package com.prize.lottery.domain.pick.repository;


import com.prize.lottery.domain.pick.model.LotteryPickDo;

public interface ILotteryPickRepository {

    void save(LotteryPickDo pick);

}
