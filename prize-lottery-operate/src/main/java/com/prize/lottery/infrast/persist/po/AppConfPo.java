package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.ConfState;
import com.prize.lottery.infrast.persist.enums.ConfType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppConfPo {

    private Long          id;
    private String        appNo;
    private String        confKey;
    private String        confVal;
    private ConfType      type;
    private String        remark;
    private ConfState     state;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
