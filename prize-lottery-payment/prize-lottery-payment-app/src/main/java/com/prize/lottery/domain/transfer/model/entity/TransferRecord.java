package com.prize.lottery.domain.transfer.model.entity;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.IdWorker;
import com.prize.lottery.application.command.event.TransCallbackEvent;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.transfer.TransferState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransferRecord implements AggregateRoot<Long> {

    private static final long serialVersionUID = -5435659677843679771L;

    private Long          id;
    private String        bizNo;
    private Long          userId;
    private String        openId;
    private PayChannel    channel;
    private String        scene;
    private String        batchNo;
    private String        transNo;
    private Long          amount;
    private TransferState state;
    private AuditState    audit;
    private String        remark;
    private String        failReason;
    private LocalDateTime latestTime;

    public TransferRecord(String bizNo,
                          Long userId,
                          String openId,
                          PayChannel channel,
                          String scene,
                          Long amount,
                          AuditState audit,
                          String remark) {
        this.bizNo      = bizNo;
        this.userId     = userId;
        this.openId     = openId;
        this.channel    = channel;
        this.scene      = scene;
        this.transNo    = String.valueOf(IdWorker.nextId());
        this.amount     = amount;
        this.remark     = remark;
        this.latestTime = LocalDateTime.now();
        this.audit      = audit;
    }

    public TransferRecord(String bizNo,
                          Long userId,
                          String openId,
                          PayChannel channel,
                          String scene,
                          String batchNo,
                          Long amount,
                          AuditState audit,
                          String remark) {
        this(bizNo, userId, openId, channel, scene, amount, audit, remark);
        this.batchNo = batchNo;
    }

    public boolean doing() {
        return this.state == TransferState.WAIT_PAY || this.state == TransferState.PROCESSING;
    }

    /**
     * 发起审核提现订单事件
     */
    public TransCallbackEvent auditEvent() {
        TransCallbackEvent event = this.buildEvent();
        event.setState(2);
        return event;
    }

    /**
     * 提现成功、失败、审核不通过回调事件
     */
    public TransCallbackEvent callbackEvent() {
        if (this.state != TransferState.SUCCESS && this.state != TransferState.FAIL) {
            return null;
        }
        TransCallbackEvent event = this.buildEvent();
        event.setMessage(this.failReason);
        int state = (audit == AuditState.REJECTED ? 0 : this.state.getState());
        event.setState(state);
        return event;
    }

    private TransCallbackEvent buildEvent() {
        TransCallbackEvent event = new TransCallbackEvent();
        event.setUserId(this.userId);
        event.setScene(this.scene);
        event.setBizNo(this.bizNo);
        event.setTransNo(this.transNo);
        event.setTimestamp(this.latestTime);
        event.setChannel(this.channel.getChannel());
        return event;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
