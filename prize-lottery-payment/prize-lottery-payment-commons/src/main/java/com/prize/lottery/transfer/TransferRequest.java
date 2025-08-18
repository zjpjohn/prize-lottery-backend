package com.prize.lottery.transfer;

import lombok.Data;

import java.util.List;

@Data
public class TransferRequest {

    /**
     * 转账条目集合
     * 支付宝-只支持单条转账
     * 微信支付-只支持批量转账
     */
    private List<TransferInfo> items;

    /*以下参数为微信批量转账参数*/
    /**
     * 微信批量转账外部流水号
     */
    private String batchNo;
    /**
     * 批量转账名称
     */
    private String batchName;
    /**
     * 批量转账备注
     */
    private String remark;
    /**
     * 批量转账总金额
     */
    private Long   total;

}
