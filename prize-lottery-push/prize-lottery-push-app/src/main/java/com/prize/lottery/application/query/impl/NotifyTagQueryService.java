package com.prize.lottery.application.query.impl;

import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.INotifyTagQueryService;
import com.prize.lottery.application.query.dto.GroupTagQuery;
import com.prize.lottery.infrast.persist.mapper.NotifyTagMapper;
import com.prize.lottery.infrast.persist.po.NotifyTagGroupPo;
import com.prize.lottery.infrast.persist.po.NotifyTagPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyTagQueryService implements INotifyTagQueryService {

    private final NotifyTagMapper notifyTagMapper;

    @Override
    public NotifyTagGroupPo getTagGroup(Long id) {
        return notifyTagMapper.getTagGroupById(id);
    }

    @Override
    public List<NotifyTagGroupPo> getAppTagGroups(Long appKey) {
        return notifyTagMapper.getTagGroupList(appKey, null);
    }

    @Override
    public Page<NotifyTagPo> getGroupTagList(GroupTagQuery query) {
        return query.from().count(notifyTagMapper::countNotifyTags).query(notifyTagMapper::getNotifyTagList);
    }
}
