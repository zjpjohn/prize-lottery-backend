package com.prize.lottery.infrast.persist.mapper;

import com.cloud.arch.page.PageCondition;
import com.prize.lottery.infrast.persist.po.NotifyTagBindPo;
import com.prize.lottery.infrast.persist.po.NotifyTagGroupPo;
import com.prize.lottery.infrast.persist.po.NotifyTagPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotifyTagMapper {

    int addTagGroup(NotifyTagGroupPo tagGroup);

    int editTagGroup(NotifyTagGroupPo tagGroup);

    NotifyTagGroupPo getTagGroupById(Long id);

    List<NotifyTagGroupPo> getTagGroupList(@Param("appKey") Long appKey, @Param("state") Integer state);

    int addNotifyTags(List<NotifyTagPo> tags);

    int editNotifyTag(NotifyTagPo tag);

    NotifyTagPo getNotifyTag(Long id);

    List<NotifyTagPo> getGroupTagList(Long groupId);

    List<NotifyTagPo> getNotifyTagList(PageCondition condition);

    int countNotifyTags(PageCondition condition);

    int addNotifyTagBind(List<NotifyTagBindPo> binds);

    int delNotifyTagBind(NotifyTagBindPo bind);

}
