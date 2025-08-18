package com.prize.lottery.vo.pl3;

import com.prize.lottery.po.pl3.Pl3IcaiPo;
import com.prize.lottery.value.MasterValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Pl3IcaiDataVo extends Pl3IcaiPo {

    private MasterValue master;
    private Integer     rank;
    private Integer     d2Rank;
    private Integer     d3Rank;

}
