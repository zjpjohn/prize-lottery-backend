package com.prize.lottery.domain.app.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.ConfCreateCmd;
import com.prize.lottery.application.command.dto.ConfModifyCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.ConfState;
import com.prize.lottery.infrast.persist.enums.ConfType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class AppConfDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 167055629154459942L;

    private Long      id;
    private String    appNo;
    private String    confKey;
    private String    confVal;
    private ConfType  valType;
    private String    remark;
    private ConfState state;

    public AppConfDo(ConfCreateCmd command, BiConsumer<ConfCreateCmd, AppConfDo> consumer) {
        this.state = ConfState.CREATED;
        consumer.accept(command, this);
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    public void modify(ConfModifyCmd command, BiConsumer<ConfModifyCmd, AppConfDo> consumer) {
        ConfState state = command.getState();
        if (state == null) {
            consumer.accept(command, this);
            return;
        }
        Set<ConfState> transitions = this.state.transitions();
        Assert.state(transitions.contains(state), ResponseHandler.DATA_STATE_ILLEGAL);
        this.state = command.getState();
    }

}
