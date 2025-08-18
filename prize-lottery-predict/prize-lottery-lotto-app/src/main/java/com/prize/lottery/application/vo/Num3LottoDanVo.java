package com.prize.lottery.application.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class Num3LottoDanVo {

    private String        period;
    private List<Integer> danList;
    private List<Integer> shiList;

    public Num3LottoDanVo(String period, List<Integer> danList, List<Integer> shiList) {
        this.period  = period;
        this.danList = danList;
        this.shiList = shiList;
    }

    public static Num3LottoDanVo empty(String period) {
        return new Num3LottoDanVo(period, Lists.newLinkedList(), Lists.newArrayList());
    }

}
