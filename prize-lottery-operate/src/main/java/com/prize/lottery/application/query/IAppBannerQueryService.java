package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.AppBannerQuery;
import com.prize.lottery.infrast.persist.po.AppBannerPo;

public interface IAppBannerQueryService {

    AppBannerPo getAppBanner(Long id);

    AppBannerPo getAppBanner(String appNo, String page);

    Page<AppBannerPo> getAppBannerList(AppBannerQuery query);
}
