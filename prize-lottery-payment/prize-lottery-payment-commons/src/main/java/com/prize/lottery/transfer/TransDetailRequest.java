package com.prize.lottery.transfer;

import lombok.Data;

@Data
public class TransDetailRequest {

    /**
     * 转账明细流水号
     */
    private String transNo;
    /**
     * 批次转账流水号
     * 微信支付查询时使用
     */
    private String batchNo;

    public TransDetailRequest(String transNo) {
        this.transNo = transNo;
    }

    public TransDetailRequest(String transNo, String batchNo) {
        this.transNo = transNo;
        this.batchNo = batchNo;
    }
}
