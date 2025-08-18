package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppAssistantPo {

    private Long          id;
    private String        appNo;
    private String        suitVer;
    private String        title;
    private String        content;
    private String        type;
    private CommonState   state;
    private Integer       sort;
    private String        remark;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
