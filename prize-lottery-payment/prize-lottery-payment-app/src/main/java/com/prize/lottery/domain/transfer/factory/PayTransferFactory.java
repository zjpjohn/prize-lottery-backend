package com.prize.lottery.domain.transfer.factory;

import com.prize.lottery.application.consumer.event.PayTransferEvent;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferBatchDo;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferOneDo;
import com.prize.lottery.domain.transfer.model.specs.TransferRuleSpec;
import com.prize.lottery.domain.transfer.repository.ITransferRuleRepository;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayTransferFactory {

    private final ITransferRuleRepository ruleRepository;

    /**
     * 创建后台转账信息
     *
     * @param event 提现事件
     */
    public PayTransferOneDo createSysPay(PayTransferEvent event) {
        String remark = this.generateRemark(PayChannel.SYS_PAY);
        return new PayTransferOneDo(event.getBizNo(), event.getUserId(), "", PayChannel.SYS_PAY, event.getScene(), event.getAmount(), AuditState.PROCESSING, remark);
    }

    /**
     * 创建支付宝转账信息
     *
     * @param event 提现事件
     */
    public PayTransferOneDo createAliPay(PayTransferEvent event) {
        //校验是否需要审核
        AuditState audit = this.satisfyAndAudit(event.getScene(), event.getAmount());
        //生成提现备注信息
        String remark = this.generateRemark(PayChannel.ALI_PAY);
        return new PayTransferOneDo(event.getBizNo(), event.getUserId(), event.getOpenId(), PayChannel.ALI_PAY, event.getScene(), event.getAmount(), audit, remark);
    }

    /**
     * 创建微信提现信息
     *
     * @param event 提现信息
     */
    public PayTransferBatchDo createWxPay(PayTransferEvent event) {
        //提现审核校验
        AuditState audit = this.satisfyAndAudit(event.getScene(), event.getAmount());
        //提现备注内容
        String remark = this.generateRemark(PayChannel.WX_PAY);

        return new PayTransferBatchDo(event.getBizNo(), event.getUserId(), event.getOpenId(), event.getScene(), PayChannel.WX_PAY, event.getAmount(), audit, remark);
    }

    /**
     * 检验用户提现金额是否需要审核
     *
     * @param scene  提现场景
     * @param amount 提现金额
     */
    private AuditState satisfyAndAudit(String scene, Long amount) {
        TransferRuleSpec ruleSpec = ruleRepository.ofScene(scene);
        return ruleSpec.needAudit(amount);
    }

    /**
     * 生成提现备注信息
     *
     * @param channel 提现渠道
     */
    private String generateRemark(PayChannel channel) {
        String date = DateTimeFormatter.ofPattern("MM月dd日").format(LocalDateTime.now());
        return date + channel.getRemark() + "收益提现";
    }

}
