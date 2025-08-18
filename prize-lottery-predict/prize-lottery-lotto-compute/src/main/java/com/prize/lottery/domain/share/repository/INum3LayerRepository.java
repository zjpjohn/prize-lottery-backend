package com.prize.lottery.domain.share.repository;


import com.prize.lottery.domain.share.model.Num3LayerFilterDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.Optional;

public interface INum3LayerRepository {

    void save(Num3LayerFilterDo aggregate);

    Optional<Num3LayerFilterDo> ofUk(String period, LotteryEnum type);

    Optional<Num3LayerFilterDo> ofId(Long id);

}
