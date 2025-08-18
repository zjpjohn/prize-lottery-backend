package com.prize.lottery.vo.fc3d;

import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.value.MasterValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Fc3dIcaiDataVo extends Fc3dIcaiPo {

    private MasterValue master;
    private Integer     rank;
    private Integer     d2Rank;
    private Integer     d3Rank;

}
