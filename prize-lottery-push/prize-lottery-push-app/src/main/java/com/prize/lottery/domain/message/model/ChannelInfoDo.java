package com.prize.lottery.domain.message.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.ChannelCreateCmd;
import com.prize.lottery.application.command.dto.ChannelModifyCmd;
import com.prize.lottery.infrast.error.ResponseErrorHandler;
import com.prize.lottery.infrast.persist.enums.ChannelScope;
import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class ChannelInfoDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 363208280079149668L;

    private Long         id;
    private String       name;
    private String       channel;
    private String       cover;
    private String       remark;
    private Integer      type;
    private ChannelScope scope;
    private Integer      remind;
    private CommonState  state;

    public ChannelInfoDo(ChannelCreateCmd command, BiConsumer<ChannelCreateCmd, ChannelInfoDo> converter) {
        this.state = CommonState.CREATED;
        converter.accept(command, this);
    }

    public void modify(ChannelModifyCmd command, BiConsumer<ChannelModifyCmd, ChannelInfoDo> converter) {
        CommonState state = command.getState();
        if (state == null) {
            Assert.state(this.state == CommonState.CREATED, ResponseErrorHandler.STATE_ILLEGAL_ERROR);
            converter.accept(command, this);
            return;
        }
        Set<CommonState> transitions = this.state.transitions();
        Assert.state(transitions.contains(state), ResponseErrorHandler.STATE_ILLEGAL_ERROR);
        this.state = state;
    }

    public boolean isAnnounce() {
        return this.type == 0;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
