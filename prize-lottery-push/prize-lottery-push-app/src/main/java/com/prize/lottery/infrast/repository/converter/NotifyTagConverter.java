package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.notify.model.NotifyTagDo;
import com.prize.lottery.domain.notify.model.NotifyTagGroupDo;
import com.prize.lottery.domain.notify.valobj.TagBind;
import com.prize.lottery.infrast.persist.po.NotifyTagBindPo;
import com.prize.lottery.infrast.persist.po.NotifyTagGroupPo;
import com.prize.lottery.infrast.persist.po.NotifyTagPo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotifyTagConverter {

    NotifyTagDo toDo(NotifyTagPo tag);

    List<NotifyTagDo> toList(List<NotifyTagPo> tags);

    NotifyTagPo toPo(NotifyTagDo tag);

    List<NotifyTagPo> toTagList(List<NotifyTagDo> tagList);

    NotifyTagGroupDo toDo(NotifyTagGroupPo tagGroup);

    NotifyTagGroupPo toPo(NotifyTagGroupDo tagGroup);

    NotifyTagBindPo toPo(TagBind bind);

    List<NotifyTagBindPo> toBindList(List<TagBind> binds);

}
