package com.prize.lottery.vo.qlc;

import com.prize.lottery.po.qlc.QlcIcaiPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QlcIcaiHistoryVo extends QlcIcaiPo {

    //开奖红球号码
    private String red;
    //开奖篮球号码
    private String blue;
}
