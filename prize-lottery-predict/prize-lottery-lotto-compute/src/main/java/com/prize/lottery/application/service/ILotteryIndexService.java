package com.prize.lottery.application.service;


import com.prize.lottery.value.BallIndex;

import java.util.List;

public interface ILotteryIndexService {

    List<BallIndex> fc3dFullIndex(String period);

    List<BallIndex> fc3dVipIndex(String period);

    void fc3dLottoIndex(String period);

    void fc3dItemIndex(String period);

    void pl3LottoIndex(String period);

    void pl3ItemIndex(String period);

    List<BallIndex> pl3FullIndex(String period);

    List<BallIndex> pl3VipIndex(String period);

}
