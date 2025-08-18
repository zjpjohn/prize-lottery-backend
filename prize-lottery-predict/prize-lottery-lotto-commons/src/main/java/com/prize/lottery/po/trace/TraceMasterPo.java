package com.prize.lottery.po.trace;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TraceMasterPo {

    private Long          id;
    private String        masterId;
    private Integer       type;
    private String        period;
    private Integer       rank;
    private String        channel;
    private LocalDateTime gmtCreate;

}
