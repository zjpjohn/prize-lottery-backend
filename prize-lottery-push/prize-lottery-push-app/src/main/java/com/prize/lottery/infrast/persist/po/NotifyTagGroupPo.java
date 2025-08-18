package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyTagGroupPo {

    private Long          id;
    private Long          appKey;
    private String        name;
    private String        tagPrefix;
    private Integer       binds;
    private Integer       tags;
    private String        remark;
    private Integer       upperBound;
    private CommonState   state;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
