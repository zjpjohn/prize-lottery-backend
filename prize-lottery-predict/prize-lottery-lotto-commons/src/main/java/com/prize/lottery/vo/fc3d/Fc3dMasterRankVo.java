package com.prize.lottery.vo.fc3d;

import com.prize.lottery.value.StatHitValue;
import com.prize.lottery.vo.BaseMasterRank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class Fc3dMasterRankVo extends BaseMasterRank {
    /**
     * 数据指标字段
     */
    private String        type;
    /**
     * 指定类型数据的命中率信息
     */
    private StatHitValue  rate;
    /**
     * 三胆命中率
     */
    private StatHitValue  dan3;
    /**
     * 7码命中率
     */
    private StatHitValue  com7;
    /**
     * 杀一码命中率
     */
    private StatHitValue  kill1;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

}
