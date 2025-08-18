package com.prize.lottery.application.command.executor.transfer;


import com.cloud.arch.executor.Executor;
import com.prize.lottery.pay.PayChannel;

public interface TransCheckExecutor extends Executor<PayChannel> {

    /**
     * 检查第三方支付转账结果
     *
     * @param transNo 转账流水号
     * @param batchNo  批量转账号
     */
    void checkTransResult(String transNo, String batchNo);

    /**
     * 回滚第三方支付转账结果
     *
     * @param transNo  转账流水号
     * @param batchNo 批量转账号
     * @param reason  回滚原因
     */
    void rollbackTransResult(String transNo, String batchNo, String reason);

}
