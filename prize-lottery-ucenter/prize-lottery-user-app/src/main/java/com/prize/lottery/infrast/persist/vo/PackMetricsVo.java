package com.prize.lottery.infrast.persist.vo;

import lombok.Data;

@Data
public class PackMetricsVo {

    //套餐编号
    private String  packNo;
    //套餐名称
    private String  packName;
    //支付总金额
    private Long    payAmt;
    //支付总人数
    private Integer payCnt;

}
