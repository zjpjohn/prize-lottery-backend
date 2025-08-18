package com.prize.lottery.domain.transfer.ability;

import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferBatchDo;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferOneDo;
import com.prize.lottery.domain.transfer.model.entity.TransferRecord;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.transfer.FundTransferExecutor;
import com.prize.lottery.transfer.TransferInfo;
import com.prize.lottery.transfer.TransferRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PayTransferExecutorAbility {

    private final EnumerableExecutorFactory<PayChannel, FundTransferExecutor> transferExecutorFactory;

    public PayTransferExecutorAbility(EnumerableExecutorFactory<PayChannel, FundTransferExecutor> transferExecutorFactory) {
        this.transferExecutorFactory = transferExecutorFactory;
    }

    /**
     * 向支付宝账户转账
     *
     * @param transfer 转账信息
     */
    public void executeAliPay(PayTransferOneDo transfer) {
        FundTransferExecutor transferExecutor = this.transferExecutorFactory.of(PayChannel.ALI_PAY);
        TransferRequest request      = new TransferRequest();
        TransferInfo    transferInfo = this.converter(transfer);
        request.setItems(Lists.newArrayList(transferInfo));
        //向支付宝发起提现请求
        transferExecutor.transfer(request);
    }

    /**
     * 向微信账户转账
     *
     * @param transfer 转账信息
     */
    public void executeWxPay(PayTransferBatchDo transfer) {
        FundTransferExecutor transferExecutor = this.transferExecutorFactory.of(PayChannel.WX_PAY);

        //批次信息
        TransferRequest request = new TransferRequest();
        request.setBatchName(transfer.getBatchName());
        request.setBatchNo(transfer.getBatchNo());
        request.setRemark(transfer.getRemark());
        request.setTotal(transfer.getTotal());
        //转账条目信息
        TransferInfo transferInfo = this.converter(transfer.getRecord());
        request.setItems(Lists.newArrayList(transferInfo));
        //向微信发起提现请求
        transferExecutor.transfer(request);
    }

    private TransferInfo converter(TransferRecord record) {
        TransferInfo transferInfo = new TransferInfo();
        transferInfo.setTransId(record.getOpenId());
        transferInfo.setTransNo(record.getTransNo());
        transferInfo.setAmount(record.getAmount());
        transferInfo.setRemark(record.getRemark());
        return transferInfo;
    }

}
