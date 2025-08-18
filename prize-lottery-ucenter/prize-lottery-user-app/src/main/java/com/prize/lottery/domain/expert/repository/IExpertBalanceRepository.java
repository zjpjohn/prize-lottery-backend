package com.prize.lottery.domain.expert.repository;


import com.prize.lottery.domain.expert.model.ExpertBalance;

import java.util.Optional;

public interface IExpertBalanceRepository {

    void save(ExpertBalance balance);

    Optional<ExpertBalance> ofId(Long userId);

    Optional<ExpertBalance> ofMaster(String masterId);

}
