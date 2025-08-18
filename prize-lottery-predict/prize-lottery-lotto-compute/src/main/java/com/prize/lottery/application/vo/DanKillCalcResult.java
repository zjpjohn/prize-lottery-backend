package com.prize.lottery.application.vo;

import lombok.Data;

import java.util.List;
import java.util.TreeMap;

@Data
public class DanKillCalcResult {

    private TreeMap<String, Double> kWeight;
    private List<String>            danList;
    private List<String>            killList;

}
