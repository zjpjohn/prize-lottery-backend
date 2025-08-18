package com.prize.lottery.domain.app.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.ContactCreateCmd;
import com.prize.lottery.application.command.dto.ContactEditCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class AppContactDo implements AggregateRoot<Long> {

    private Long        id;
    private String      appNo;
    private String      name;
    private String      qrImg;
    private String      remark;
    private CommonState state;

    public AppContactDo(ContactCreateCmd cmd, BiConsumer<ContactCreateCmd, AppContactDo> converter) {
        this.state = CommonState.CREATED;
        converter.accept(cmd, this);
    }

    public void modify(ContactEditCmd cmd, BiConsumer<ContactEditCmd, AppContactDo> converter) {
        CommonState state = cmd.getState();
        if (state == null) {
            converter.accept(cmd, this);
            return;
        }
        Set<CommonState> transitions = this.state.transitions();
        Assert.state(transitions.contains(state), ResponseHandler.DATA_STATE_ILLEGAL);
        this.state = state;
    }

    public boolean hasRemoved() {
        return this.state == CommonState.INVALID;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
