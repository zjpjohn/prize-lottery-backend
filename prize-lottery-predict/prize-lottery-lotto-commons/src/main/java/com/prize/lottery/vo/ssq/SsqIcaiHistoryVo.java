package com.prize.lottery.vo.ssq;

import com.prize.lottery.po.ssq.SsqIcaiPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SsqIcaiHistoryVo extends SsqIcaiPo {

    //开奖红球号码
    private String red;
    //开奖篮球号码
    private String blue;
}
