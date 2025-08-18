package com.prize.lottery.application.query.service;


import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.prize.lottery.po.master.AvatarInfoPo;

import java.util.List;

public interface IAvatarQueryService {

    List<String> getRandomAvatars(Integer limit);

    Page<AvatarInfoPo> getAvatarInfoList(PageQuery query);

}
