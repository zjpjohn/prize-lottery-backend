package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.app.model.AppBannerDo;
import com.prize.lottery.infrast.persist.po.AppBannerPo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppBannerConverter {

    AppBannerPo toPo(AppBannerDo bannerDo);

    AppBannerDo toDo(AppBannerPo bannerPo);
}
