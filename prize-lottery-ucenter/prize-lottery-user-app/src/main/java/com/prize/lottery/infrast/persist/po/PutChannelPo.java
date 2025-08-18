package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.ChannelState;
import com.prize.lottery.infrast.persist.enums.ChannelType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PutChannelPo {

    private Long          id;
    private String        appNo;
    private String       bizNo;
    private ChannelType  type;
    private ChannelState state;
    private Integer      targetCnt;
    private String        thirdId;
    private String        remark;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
