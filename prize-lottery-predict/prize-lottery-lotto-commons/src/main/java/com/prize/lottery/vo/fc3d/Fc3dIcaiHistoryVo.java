package com.prize.lottery.vo.fc3d;

import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Fc3dIcaiHistoryVo extends Fc3dIcaiPo {

    ///开奖号码
    private String red;

}
