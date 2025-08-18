package com.prize.lottery.po.fc3d;

import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.po.share.BaseLottoCensus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Fc3dLottoCensusPo extends BaseLottoCensus {

    /**
     * 福彩3D统一字段
     */
    private Fc3dChannel channel;

}
