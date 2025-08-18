package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.NewsContent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotterySkillPo {

    private Long          id;
    private String        sha;
    private String        seq;
    private LotteryEnum   type;
    private String        title;
    private String        header;
    private String        summary;
    private NewsContent   content;
    private Integer       state;
    private Integer       browse;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
