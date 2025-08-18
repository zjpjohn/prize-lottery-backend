package com.prize.lottery.domain.expert.repository;


import com.prize.lottery.domain.expert.model.ExpertAcct;

public interface IExpertAcctRepository {

    void save(ExpertAcct account);

    ExpertAcct ofUser(Long userId);

    ExpertAcct ofMaster(String masterId);

}
