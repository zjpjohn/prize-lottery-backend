package com.prize.lottery.po.lottery;

import com.prize.lottery.value.NewsContent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryNewsPo {

    private Long          id;
    private String        seq;
    private String        sha;
    private String        title;
    private String        source;
    private String        header;
    private String        type;
    private String        author;
    private String      summary;
    private NewsContent content;
    private Integer     browse;
    private Integer       homed;
    private Integer       state;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;
}
