package com.prize.lottery.transfer;


import com.cloud.arch.executor.Executor;
import com.prize.lottery.pay.PayChannel;

public interface FundTransferExecutor extends Executor<PayChannel> {

    /**
     * 转账操作
     *
     * @param request 转账请求
     */
    TransferResponse transfer(TransferRequest request);

    /**
     * 查询转账业务单据
     *
     * @param request 查询请求
     */
    TransDetailResponse query(TransDetailRequest request);

    /**
     * 查询批量转账的批次信息
     * 注：批量转账-只针对微信转账
     *
     * @param request 查询批次请求
     */
    default TransBatchResponse query(TransBatchRequest request) {
        return null;
    }

}
