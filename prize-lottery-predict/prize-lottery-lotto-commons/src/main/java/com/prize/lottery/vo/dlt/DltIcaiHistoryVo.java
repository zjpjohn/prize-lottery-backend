package com.prize.lottery.vo.dlt;

import com.prize.lottery.po.dlt.DltIcaiPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DltIcaiHistoryVo extends DltIcaiPo {

    //开奖红球号码
    private String red;
    //开奖篮球号码
    private String blue;
}
