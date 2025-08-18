package com.prize.lottery.domain.omit.repository;


import com.prize.lottery.domain.omit.model.LottoKl8OmitDo;

import java.util.List;
import java.util.Optional;

public interface ILottoKl8OmitRepository {

    void save(LottoKl8OmitDo omit);

    void saveBatch(List<LottoKl8OmitDo> omits);

    Optional<LottoKl8OmitDo> latestOmit();

    LottoKl8OmitDo ofPeriod(String period);

}
