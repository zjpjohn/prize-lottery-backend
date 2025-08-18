package com.prize.lottery.po.pl3;

import com.prize.lottery.value.ComRecommend;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Pl3ComRecommendPo {

    private Long          id;
    private String       period;
    private ComRecommend zu6;
    private ComRecommend zu3;
    private Integer       hit;
    private Integer       type;
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;

}
