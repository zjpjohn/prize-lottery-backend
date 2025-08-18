package com.prize.lottery.domain.transfer.model.aggregate;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.IdWorker;
import com.prize.lottery.application.command.event.TransCallbackEvent;
import com.prize.lottery.domain.transfer.model.entity.TransferRecord;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.transfer.TransBatchResponse;
import com.prize.lottery.transfer.TransBatchState;
import com.prize.lottery.transfer.TransDetailResponse;
import com.prize.lottery.transfer.TransferState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PayTransferBatchDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -4736069513558098639L;

    private Long            id;
    private String          bizNo;
    private String          batchNo;
    private String          batchName;
    private String          remark;
    private String          scene;
    private PayChannel      channel;
    private Long            total;
    private Integer         totalNum;
    private TransBatchState state;
    private AuditState      audit;
    private Long            operId;
    private Integer         operType;
    private String          closeReason;
    private Long            successAmount;
    private Integer         successNum;
    private Long            failAmount;
    private Integer         failNum;
    private LocalDateTime  latestTime;
    private TransferRecord record;

    public PayTransferBatchDo(String bizNo,
                              Long userId,
                              String openId,
                              String scene,
                              PayChannel channel,
                              Long amount,
                              AuditState audit,
                              String remark) {
        this.bizNo      = bizNo;
        this.batchName  = remark;
        this.remark     = remark;
        this.scene      = scene;
        this.channel    = channel;
        this.operId     = userId;
        this.operType   = 1;
        this.total      = amount;
        this.totalNum   = 1;
        this.audit      = audit;
        this.state      = TransBatchState.ACCEPTED;
        this.latestTime = LocalDateTime.now();
        this.batchNo    = String.valueOf(IdWorker.nextId());
        this.record     = new TransferRecord(bizNo, userId, openId, channel, scene, batchNo, amount, audit, remark);
    }

    public TransCallbackEvent rollback(LocalDateTime latestTime, String reason) {
        //批量状态更改
        this.state      = TransBatchState.CLOSED;
        this.latestTime = latestTime;
        //记录状态更改
        this.record.setState(TransferState.FAIL);
        this.record.setLatestTime(latestTime);
        this.closeReason = reason;
        this.record.setFailReason(reason);
        //返回回调事件
        return this.record.callbackEvent();
    }

    public TransCallbackEvent check(TransBatchResponse response, TransDetailResponse result) {
        this.state         = response.getState();
        this.successAmount = response.getSuccessAmount();
        this.successNum    = response.getSuccessNum();
        this.failAmount    = response.getFailAmount();
        this.failNum       = response.getFailNum();
        if (this.state == TransBatchState.CLOSED) {
            this.closeReason = response.getCloseReason();
            this.record.setState(TransferState.FAIL);
            this.record.setFailReason(this.closeReason);
        }
        if (this.state == TransBatchState.FINISHED && result != null) {
            this.record.setState(result.getState());
            this.record.setLatestTime(response.getLatestTime());
            if (result.getState() == TransferState.FAIL) {
                this.record.setFailReason(result.getFailReason());
            }
        }
        return this.record.callbackEvent();
    }

    /**
     * 转账审核
     *
     * @param state  审核状态
     * @param reason 审核不通过原因
     */
    public void audit(AuditState state, String reason) {
        this.audit      = state;
        this.latestTime = LocalDateTime.now();
        this.record.setAudit(this.audit);
        this.record.setLatestTime(this.latestTime);
        //审核拒绝通过进行关单
        if (this.audit == AuditState.REJECTED) {
            this.state       = TransBatchState.CLOSED;
            this.closeReason = reason;
            this.record.setState(TransferState.FAIL);
            this.record.setFailReason(reason);
        }
    }

    /**
     * 提现是否在进行中
     */
    public boolean doing() {
        return this.state == TransBatchState.ACCEPTED || this.state == TransBatchState.PROCESSING;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
