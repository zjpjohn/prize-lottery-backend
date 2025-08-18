package com.prize.lottery.vo.pl3;

import com.prize.lottery.po.pl3.Pl3IcaiPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Pl3IcaiHistoryVo extends Pl3IcaiPo {

    ///开奖号码
    private String red;
}
