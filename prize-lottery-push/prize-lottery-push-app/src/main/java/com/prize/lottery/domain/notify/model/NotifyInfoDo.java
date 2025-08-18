package com.prize.lottery.domain.notify.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.aggregate.Ignore;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.NotifyInfoCreateCmd;
import com.prize.lottery.application.command.dto.NotifyInfoModifyCmd;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.enums.NotifyNotice;
import com.prize.lottery.infrast.persist.enums.OpenType;
import com.prize.lottery.infrast.persist.enums.PushState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Data
@NoArgsConstructor
public class NotifyInfoDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 4541665479183456154L;

    private Long         id;
    private Long         appKey;
    private Long         groupId;
    private Integer      type;
    private Integer      hour;
    private String       title;
    private String       body;
    private NotifyNotice notice;
    private Integer      barType;
    private Integer      barPriority;
    private String       extParams;
    private OpenType     openType;
    private String       openUrl;
    private String       openActivity;
    private String       channel;
    private CommonState  state;
    private Integer      online;
    private String       remark;
    @Ignore
    private NotifyTaskDo notifyTask;

    public NotifyInfoDo(Long appKey,
                        NotifyInfoCreateCmd command,
                        BiConsumer<NotifyInfoCreateCmd, NotifyInfoDo> consumer) {
        this.appKey = appKey;
        this.online = 1;
        this.state  = CommonState.CREATED;
        consumer.accept(command, this);
    }

    public void modify(NotifyInfoModifyCmd command, BiConsumer<NotifyInfoModifyCmd, NotifyInfoDo> consumer) {
        CommonState state = command.getState();
        if (state == null) {
            Assert.state(this.state == CommonState.CREATED, ResponseErrorHandler.STATE_ILLEGAL_ERROR);
            consumer.accept(command, this);
            return;
        }
        boolean canTransition = this.state.transitions().contains(state);
        Assert.state(canTransition, ResponseErrorHandler.STATE_ILLEGAL_ERROR);
        this.state = state;
    }

    /**
     * 当前推送信息是否可推送
     */
    public Boolean canPush() {
        return CommonState.USING.equals(this.state);
    }

    /**
     * 创建通知推送
     *
     * @param tagList 标签集合
     */
    public Boolean notifyPush(List<String> tagList, Function<NotifyTaskDo, String> executor) {
        LocalDateTime current    = LocalDateTime.now();
        LocalDateTime expectTime = current.withHour(this.hour).withMinute(0).withSecond(0);
        //当前推送时间不超过预计推送时间
        if (current.isAfter(expectTime)) {
            return Boolean.FALSE;
        }
        NotifyTaskDo notifyTask = new NotifyTaskDo();
        notifyTask.setGroupId(this.groupId);
        notifyTask.setNotifyId(this.id);
        notifyTask.setAppKey(this.appKey);
        notifyTask.setPlatform("ANDROID");
        notifyTask.setTags(tagList.size());
        notifyTask.setTagList(String.join(",", tagList));
        notifyTask.setExpectTime(expectTime);
        this.notifyTask = notifyTask;
        String messageId = executor.apply(this.notifyTask);
        if (messageId != null) {
            notifyTask.setState(PushState.CREATED);
            this.notifyTask.setMessageId(messageId);
        } else {
            this.notifyTask.setState(PushState.FAILED);
        }
        return Boolean.TRUE;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
