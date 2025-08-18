package com.prize.lottery.application.query;


import com.cloud.arch.page.Page;
import com.prize.lottery.application.query.dto.GroupTagQuery;
import com.prize.lottery.infrast.persist.po.NotifyTagGroupPo;
import com.prize.lottery.infrast.persist.po.NotifyTagPo;

import java.util.List;

public interface INotifyTagQueryService {

    NotifyTagGroupPo getTagGroup(Long id);

    List<NotifyTagGroupPo> getAppTagGroups(Long appKey);

    Page<NotifyTagPo> getGroupTagList(GroupTagQuery query);

}
