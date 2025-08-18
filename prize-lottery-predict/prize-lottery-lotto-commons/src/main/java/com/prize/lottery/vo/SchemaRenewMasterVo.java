package com.prize.lottery.vo;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.MasterValue;
import lombok.Data;

/**
 * 方案更新专家信息
 */
@Data
public class SchemaRenewMasterVo {

    /**
     * 专家标识
     */
    private String      masterId;
    /**
     * 专家信息
     */
    private MasterValue master;
    /**
     * 更新方案期号
     */
    private String      period;
    /**
     * 更新方案彩种
     */
    private LotteryEnum type;

}
