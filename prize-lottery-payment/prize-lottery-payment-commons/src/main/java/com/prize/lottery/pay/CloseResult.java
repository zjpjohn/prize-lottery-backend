package com.prize.lottery.pay;

import lombok.Data;

@Data
public class CloseResult {

    //订单编号
    private String  orderNo;
    //是否关闭成功
    private Boolean result;

}
