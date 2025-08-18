package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.domain.app.model.AppBannerDo;
import com.prize.lottery.domain.app.repository.IAppBannerRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.mapper.AppBannerMapper;
import com.prize.lottery.infrast.persist.po.AppBannerPo;
import com.prize.lottery.infrast.repository.converter.AppBannerConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppBannerRepository implements IAppBannerRepository {

    private final AppBannerMapper    mapper;
    private final AppBannerConverter converter;

    @Override
    public void save(Aggregate<Long, AppBannerDo> aggregate) {
        AppBannerDo root = aggregate.getRoot();
        if (root.isNew()) {
            AppBannerPo bannerPo = converter.toPo(root);
            mapper.addAppBanner(bannerPo);
            return;
        }
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editAppBanner);
    }

    @Override
    public Aggregate<Long, AppBannerDo> ofId(Long id) {
        return Optional.ofNullable(mapper.getAppBannerById(id))
                       .map(converter::toDo)
                       .map(AggregateFactory::create)
                       .orElseThrow(ResponseHandler.BANNER_NOT_EXIST);
    }

}
