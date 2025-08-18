package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.ChannelScope;
import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChannelInfoPo {

    private Long          id;
    private String        name;
    private String        channel;
    private String        cover;
    private String        remark;
    private Integer      type;
    private ChannelScope scope;
    private CommonState  state;
    private Integer      remind;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
