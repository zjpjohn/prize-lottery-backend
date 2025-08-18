package com.prize.lottery.domain.notify.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.prize.lottery.application.command.dto.NotifyAppCreateCmd;
import com.prize.lottery.application.command.dto.NotifyAppModifyCmd;
import lombok.Data;

import java.util.function.BiConsumer;

@Data
public class NotifyAppDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -1846798179987332165L;

    private Long   id;
    private String appNo;
    private String appName;
    private Long   appKey;
    private String platform;
    private String remark;

    public NotifyAppDo(NotifyAppCreateCmd command, BiConsumer<NotifyAppCreateCmd, NotifyAppDo> consumer) {
        consumer.accept(command, this);
    }

    public void modify(NotifyAppModifyCmd command, BiConsumer<NotifyAppModifyCmd, NotifyAppDo> consumer) {
        consumer.accept(command, this);
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }


}
