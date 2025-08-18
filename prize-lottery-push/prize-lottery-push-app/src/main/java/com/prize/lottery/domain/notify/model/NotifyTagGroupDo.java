package com.prize.lottery.domain.notify.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.aggregate.Ignore;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.application.command.dto.TagGroupCreateCmd;
import com.prize.lottery.application.command.dto.TagGroupModifyCmd;
import com.prize.lottery.domain.notify.valobj.TagBind;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Data
public class NotifyTagGroupDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 4466849416529682828L;

    private Long              id;
    //应用key
    private Long              appKey;
    //标签组名称
    private String            name;
    //标签名称前缀
    private String            tagPrefix;
    //绑定设备总数
    private Integer           binds;
    //标签组描述信息
    private String            remark;
    //标签组状态
    private CommonState       state;
    //单个标签绑定设备上限
    private Integer           upperBound;
    //标签总数
    private Integer           tags;
    //标签集合
    @Ignore
    private List<NotifyTagDo> tagList;
    //绑定设备集合
    @Ignore
    private List<TagBind>     bindList;
    //选中绑定的标签
    @Ignore
    private NotifyTagDo       pickedTag;

    public NotifyTagGroupDo(TagGroupCreateCmd command, BiConsumer<TagGroupCreateCmd, NotifyTagGroupDo> consumer) {
        this.binds = 0;
        this.state = CommonState.CREATED;
        consumer.accept(command, this);
        this.tagList = this.generateTags(20, 0);
        this.tags    = this.tagList.size();
    }

    public void modify(TagGroupModifyCmd command, BiConsumer<TagGroupModifyCmd, NotifyTagGroupDo> consumer) {
        CommonState state = command.getState();
        if (state == null) {
            Assert.state(this.state == CommonState.CREATED, ResponseErrorHandler.STATE_ILLEGAL_ERROR);
            consumer.accept(command, this);
            return;
        }
        boolean canTransition = this.state.transitions().contains(state);
        Assert.state(canTransition, ResponseErrorHandler.STATE_ILLEGAL_ERROR);
        //已有设备绑定的标签组不允许取消
        if (this.state.equals(CommonState.USING) && state.equals(CommonState.CREATED)) {
            Assert.state(this.binds == 0, ResponseErrorHandler.NOTIFY_TAG_HAS_BIND);
        }
        this.state = state;
    }

    /**
     * 标签成倍扩容
     */
    public void dilatateTags() {
        List<NotifyTagDo> dilatateTags = this.generateTags(20, this.tags);
        dilatateTags.forEach(tag -> tag.setGroupId(this.id));
        this.tags = this.tags + dilatateTags.size();
        this.tagList.addAll(dilatateTags);
    }

    /**
     * 绑定设备集合
     *
     * @param devices 设备集合
     */
    public Boolean bindDevices(List<String> devices) {
        //选中绑定设备的标签
        int deviceSize = devices.size();
        this.pickedTag = this.tagList.stream()
                                     .filter(tag -> tag.getBinds() < this.upperBound - deviceSize)
                                     .min(Comparator.comparingInt(NotifyTagDo::getBinds))
                                     .orElse(null);
        if (this.pickedTag == null) {
            //没有可绑定的标签绑定失败
            return false;
        }
        //生成绑定列表
        this.bindList = devices.stream()
                               .map(d -> new TagBind(appKey, id, this.pickedTag.getId(), d))
                               .collect(Collectors.toList());
        //增加绑定总数量
        this.binds = this.binds + deviceSize;
        //当前标签绑定数量计算
        this.pickedTag.incrBinds(deviceSize);
        return true;
    }

    /**
     * 当前标签组是否可以推送
     */
    public Boolean canNotify() {
        return this.state.equals(CommonState.USING);
    }

    /**
     * 生成标签集合
     *
     * @param size     生成标签数量
     * @param tagIndex 标签下标
     */
    private List<NotifyTagDo> generateTags(int size, int tagIndex) {
        List<NotifyTagDo> tags = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            int    tagIdx  = tagIndex + i;
            String tagName = this.tagPrefix + "_" + tagIdx;
            tags.add(new NotifyTagDo(appKey, tagName));
        }
        return tags;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
