package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.PutState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PutRecordPo {

    private Long          id;
    private String        appNo;
    private String        channel;
    private String        code;
    private String        invUri;
    private Long          expectAmt;
    private Integer  userCnt;
    private PutState state;
    private String   remark;
    private LocalDateTime putTime;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
