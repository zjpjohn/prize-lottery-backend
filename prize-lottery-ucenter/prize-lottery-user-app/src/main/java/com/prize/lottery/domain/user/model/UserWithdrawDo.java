package com.prize.lottery.domain.user.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.prize.lottery.application.command.dto.WithdrawDto;
import com.prize.lottery.domain.withdraw.specs.WithdrawValObj;
import com.prize.lottery.infrast.persist.enums.PayChannel;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.enums.WithdrawState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.BiConsumer;

@Data
@NoArgsConstructor
public class UserWithdrawDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 5441658528284937256L;

    private Long          id;
    private String        seqNo;
    private String        transNo;
    private Long          userId;
    private TransferScene scene;
    private Long          withdraw;
    private Long          money;
    private PayChannel    channel;
    private WithdrawState state;
    private String        message;

    public UserWithdrawDo(WithdrawDto command, BiConsumer<WithdrawDto, UserWithdrawDo> converter) {
        this.state = WithdrawState.APPLY;
        converter.accept(command, this);
    }

    public void modify(String transNo, WithdrawState state, String message) {
        this.transNo = transNo;
        this.state   = state;
        this.message = message;
    }

    public WithdrawValObj toValObj() {
        return new WithdrawValObj(this.seqNo, this.userId, this.scene, this.withdraw, this.money);
    }

    /**
     * 提现状态是否为进行中
     */
    public boolean isDoing() {
        return this.state == WithdrawState.APPLY || this.state == WithdrawState.ADOPTED;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
