package com.prize.lottery.domain.app.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.prize.lottery.application.command.dto.AppCommentCreateCmd;
import com.prize.lottery.application.command.dto.AppCommentEditCmd;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class AppCommentDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -7447055589246560006L;

    private Long          id;
    private String        appNo;
    private String        name;
    private String        avatar;
    private Integer       rate;
    private String        comment;
    private LocalDateTime cmtTime;

    public AppCommentDo(AppCommentCreateCmd command, BiConsumer<AppCommentCreateCmd, AppCommentDo> converter) {
        converter.accept(command, this);
    }

    public void modify(AppCommentEditCmd command, BiConsumer<AppCommentEditCmd, AppCommentDo> converter) {
        converter.accept(command, this);
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
