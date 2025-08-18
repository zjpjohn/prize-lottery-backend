package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.IAppBannerQueryService;
import com.prize.lottery.application.query.dto.AppBannerQuery;
import com.prize.lottery.infrast.persist.mapper.AppBannerMapper;
import com.prize.lottery.infrast.persist.po.AppBannerPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AppBannerQueryService implements IAppBannerQueryService {

    private final AppBannerMapper appBannerMapper;

    @Override
    public AppBannerPo getAppBanner(Long id) {
        return appBannerMapper.getAppBannerById(id);
    }

    @Override
    public AppBannerPo getAppBanner(String appNo, String page) {
        return appBannerMapper.getAppBannerByUk(appNo, page);
    }

    @Override
    public Page<AppBannerPo> getAppBannerList(AppBannerQuery query) {
        return query.from().count(appBannerMapper::countAppBanners).query(appBannerMapper::getAppBannerList);
    }
}
