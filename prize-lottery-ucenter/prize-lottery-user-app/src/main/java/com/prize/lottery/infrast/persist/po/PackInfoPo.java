package com.prize.lottery.infrast.persist.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prize.lottery.infrast.persist.enums.PackState;
import com.prize.lottery.infrast.persist.enums.TimeUnit;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PackInfoPo {

    private Long          id;
    //套餐编号
    private String        seqNo;
    //套餐名称
    private String        name;
    //备注描述信息
    private String        remark;
    //套餐价格
    private Long          price;
    //套餐折扣价格
    private Long      discount;
    //套餐市场时间单位
    private TimeUnit  timeUnit;
    //套餐状态
    private PackState state;
    //套餐是否优先
    private Integer   priority;
    //是否为试用套餐
    private Integer       onTrial;
    //套餐创建时间
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime gmtCreate;
    //更新时间
    private LocalDateTime gmtModify;

    public boolean isTrial() {
        return onTrial != null && onTrial == 1;
    }

}
