package com.prize.lottery.domain.transfer.model.aggregate;

import com.prize.lottery.application.command.event.TransCallbackEvent;
import com.prize.lottery.domain.transfer.model.entity.TransferRecord;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.transfer.TransferState;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class PayTransferOneDo extends TransferRecord {

    private static final long serialVersionUID = -2591380188063751741L;

    public PayTransferOneDo(String bizNo,
                            Long userId,
                            String openId,
                            PayChannel channel,
                            String scene,
                            Long amount,
                            AuditState audit,
                            String remark) {
        super(bizNo, userId, openId, channel, scene, amount, audit, remark);
    }

    /**
     * 更新转账状态
     *
     * @param state      转账状态
     * @param latestTime 最新变更事件
     * @param reason     转账失败时失败原因
     */
    public TransCallbackEvent check(TransferState state, LocalDateTime latestTime, String reason) {
        this.setState(state);
        this.setLatestTime(latestTime);
        if (state == TransferState.FAIL && StringUtils.hasText(reason)) {
            this.setFailReason(reason);
        }
        return this.callbackEvent();
    }

    /**
     * 转账审核
     *
     * @param state  是否审核通过
     * @param reason 审核不通过时，失败原因
     */
    public void audit(AuditState state, String reason) {
        this.setAudit(state);
        this.setLatestTime(LocalDateTime.now());
        if (state == AuditState.REJECTED) {
            //审核拒绝通过进行关单
            this.setState(TransferState.FAIL);
            this.setFailReason(reason);
            return;
        }
        if (state == AuditState.ADOPTED && this.getChannel() == PayChannel.SYS_PAY) {
            //后台付款渠道时，审核通过设置转账成功
            this.setState(TransferState.SUCCESS);
        }
    }

}
