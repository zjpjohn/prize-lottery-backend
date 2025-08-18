package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppContactPo {

    private Long          id;
    private String        appNo;
    private String        name;
    private String        qrImg;
    private String        remark;
    private CommonState   state;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
