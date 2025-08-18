package com.prize.lottery.domain.app.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.app.model.AppAssistantDo;
import com.prize.lottery.infrast.persist.valobj.AssistantApp;

public interface IAppAssistantRepository {

    void save(Aggregate<Long, AppAssistantDo> aggregate);

    Aggregate<Long, AppAssistantDo> ofId(Long id);

    Integer latestSort(String appNo);

    AssistantApp ofApp(String appNo);

}
