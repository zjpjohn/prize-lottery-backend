package com.prize.lottery.domain.app.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.app.model.AppBannerDo;

public interface IAppBannerRepository {

    void save(Aggregate<Long, AppBannerDo> aggregate);

    Aggregate<Long, AppBannerDo> ofId(Long id);

}
