package com.prize.lottery.transfer;

import lombok.Data;

@Data
public class TransBatchRequest {

    /**
     * 转账批次号
     */
    private String  batchNo;
    /**
     * 起始位置
     */
    private Integer offset;
    /**
     * 返回最大条目数
     */
    private Integer limit;

    public TransBatchRequest(String batchNo, Integer offset, Integer limit) {
        this.batchNo = batchNo;
        this.offset  = offset;
        this.limit   = limit;
    }

    public TransBatchRequest(String batchNo) {
        this(batchNo, 0, 10);
    }
}
