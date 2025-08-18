package com.prize.lottery.transfer;

import lombok.Data;

@Data
public class TransferInfo {

    /**
     * 转账账户id
     * 微信-openId,支付宝-alipayId
     */
    private String  transId;
    /**
     * 外部传入业务编号
     */
    private String  transNo;
    /**
     * 转账金额，单位-分
     * 微信支付-转账单位为分
     * 支付宝-需要转换成元
     */
    private Long amount;
    /**
     * 转账备注信息
     */
    private String  remark;

}
