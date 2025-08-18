package com.prize.lottery.po.fc3d;

import com.prize.lottery.value.ComRecommend;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Fc3dComRecommendPo {

    private Long          id;
    private String       period;
    private ComRecommend zu6;
    private ComRecommend zu3;
    private Integer       type;
    private Integer       hit;
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;

}
