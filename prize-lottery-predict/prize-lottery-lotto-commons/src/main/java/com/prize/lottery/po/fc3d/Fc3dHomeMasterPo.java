package com.prize.lottery.po.fc3d;

import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Fc3dHomeMasterPo {

    private Long          id;
    /**
     * 期号
     */
    private String        period;
    /**
     * 专家标识
     */
    private String      masterId;
    /**
     * 字段指标类型
     */
    private Fc3dChannel type;
    /**
     * 专家排名
     */
    private Integer     rank;
    /**
     * 命中率信息
     */
    private StatHitValue  rate;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

}
