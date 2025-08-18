package com.prize.lottery.application.query.service.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.prize.lottery.application.query.service.IAvatarQueryService;
import com.prize.lottery.mapper.AvatarInfoMapper;
import com.prize.lottery.po.master.AvatarInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvatarQueryService implements IAvatarQueryService {

    private final AvatarInfoMapper avatarInfoMapper;

    @Override
    public List<String> getRandomAvatars(Integer limit) {
        return avatarInfoMapper.getRandomAvatars(limit);
    }

    @Override
    public Page<AvatarInfoPo> getAvatarInfoList(PageQuery query) {
        return query.from().count(avatarInfoMapper::countAvatarList).query(avatarInfoMapper::getAvatarList);
    }

}
