package com.prize.lottery.domain.channel.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.PutChannelCreateCmd;
import com.prize.lottery.application.command.dto.PutChannelEditCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.ChannelState;
import com.prize.lottery.infrast.persist.enums.ChannelType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class PutChannelDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -7096786547350658120L;

    private Long         id;
    private String       appNo;
    private String       bizNo;
    private ChannelType  type;
    private ChannelState state;
    private Integer      targetCnt;
    private String       thirdId;
    private String       remark;

    public PutChannelDo(PutChannelCreateCmd command, BiConsumer<PutChannelCreateCmd, PutChannelDo> converter) {
        this.bizNo = String.valueOf(IdWorker.nextId());
        this.state = ChannelState.CREATED;
        converter.accept(command, this);
    }

    public void modify(PutChannelEditCmd command, BiConsumer<PutChannelEditCmd, PutChannelDo> converter) {
        ChannelState newState = command.getState();
        if (newState == null) {
            Assert.state(this.state == ChannelState.CREATED, ResponseHandler.DATA_STATE_ILLEGAL);
            converter.accept(command, this);
            return;
        }
        Set<ChannelState> transitions = this.state.transitions();
        Assert.state(transitions.contains(newState), ResponseHandler.DATA_STATE_ILLEGAL);
        this.state = newState;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
