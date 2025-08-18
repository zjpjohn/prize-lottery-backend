package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.notify.model.NotifyTagDo;
import com.prize.lottery.domain.notify.model.NotifyTagGroupDo;
import com.prize.lottery.domain.notify.repository.ITagGroupRepository;
import com.prize.lottery.domain.notify.valobj.TagBind;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.infrast.persist.mapper.NotifyTagMapper;
import com.prize.lottery.infrast.persist.po.NotifyTagBindPo;
import com.prize.lottery.infrast.persist.po.NotifyTagGroupPo;
import com.prize.lottery.infrast.persist.po.NotifyTagPo;
import com.prize.lottery.infrast.repository.converter.NotifyTagConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TagGroupRepository implements ITagGroupRepository {

    private final NotifyTagMapper    mapper;
    private final NotifyTagConverter converter;

    @Override
    public void save(Aggregate<Long, NotifyTagGroupDo> aggregate) {
        NotifyTagGroupDo root = aggregate.getRoot();
        if (root.isNew()) {
            //保存标签组信息
            NotifyTagGroupPo tagGroup = converter.toPo(root);
            mapper.addTagGroup(tagGroup);
            //保存标签集合信息
            List<NotifyTagPo> tagList = converter.toTagList(root.getTagList());
            tagList.forEach(tag -> tag.setGroupId(tagGroup.getId()));
            mapper.addNotifyTags(tagList);
            return;
        }
        //更新标签组信息
        aggregate.ifChanged().map(converter::toPo).ifPresent(mapper::editTagGroup);
        //标签新增扩容
        List<NotifyTagDo> tagList = root.getTagList()
                                        .stream()
                                        .filter(e -> e.getId() == null)
                                        .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(tagList)) {
            List<NotifyTagPo> toTagList = converter.toTagList(tagList);
            mapper.addNotifyTags(toTagList);
        }
        //设备绑定标签
        List<TagBind> bindList = root.getBindList();
        if (!CollectionUtils.isEmpty(bindList)) {
            List<NotifyTagBindPo> binds = converter.toBindList(bindList);
            mapper.addNotifyTagBind(binds);
            NotifyTagPo notifyTag = converter.toPo(root.getPickedTag());
            mapper.editNotifyTag(notifyTag);
        }
    }

    @Override
    public Aggregate<Long, NotifyTagGroupDo> ofId(Long id) {
        NotifyTagGroupPo group = mapper.getTagGroupById(id);
        Assert.notNull(group, ResponseErrorHandler.TAG_GROUP_NONE);
        NotifyTagGroupDo groupDo = converter.toDo(group);

        List<NotifyTagPo> tagList = mapper.getGroupTagList(group.getId());
        groupDo.setTagList(converter.toList(tagList));

        return AggregateFactory.create(groupDo);
    }

    @Override
    public Boolean existTagGroup(Long groupId) {
        return mapper.getTagGroupById(groupId) != null;
    }
}
