package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.AppBannerPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppBannerMapper {

    int addAppBanner(AppBannerPo banner);

    int editAppBanner(AppBannerPo banner);

    AppBannerPo getAppBannerById(Long id);

    AppBannerPo getAppBannerByUk(@Param("appNo") String appNo, @Param("page") String page);

    List<AppBannerPo> getAppBannerList(PageCondition condition);

    int countAppBanners(PageCondition condition);

}
