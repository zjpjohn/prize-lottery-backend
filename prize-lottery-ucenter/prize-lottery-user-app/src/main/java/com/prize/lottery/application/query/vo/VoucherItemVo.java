package com.prize.lottery.application.query.vo;

import lombok.Data;

@Data
public class VoucherItemVo {

    //代金券标识
    private String  seqNo;
    //代金券名称
    private String  name;
    //代金券金额
    private Integer voucher;
    //代金券过期时间:0-永久有效,>0-领取后有效天数
    private Integer expire;

}
