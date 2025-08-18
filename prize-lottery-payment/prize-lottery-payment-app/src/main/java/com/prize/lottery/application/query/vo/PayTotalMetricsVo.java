package com.prize.lottery.application.query.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PayTotalMetricsVo {

    //订单总计统计数据
    private TotalMetricsItem order;
    //提现总计统计数据
    private TotalMetricsItem transfer;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TotalMetricsItem {

        //总金额
        private Long    totalAmt = 0L;
        //总笔数
        private Integer totalCnt = 0;

    }
}
