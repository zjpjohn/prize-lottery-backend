package com.prize.lottery.infrast.persist.vo;

import lombok.Data;

@Data
public class UserActiveIndexVo {

    private Long    userId;
    //近10天活跃天数
    private Integer day10;
    //近15天活跃天数
    private Integer day15;
    //近20天活跃天数
    private Integer day20;
    //近30天活跃天数
    private Integer day30;

}
