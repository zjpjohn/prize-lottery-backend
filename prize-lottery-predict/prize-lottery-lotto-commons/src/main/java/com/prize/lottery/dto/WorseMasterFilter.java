package com.prize.lottery.dto;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class WorseMasterFilter {

    private List<String> periods;
    private List<String> items;
    private Integer      size;
    private Integer      throttle;

    public static WorseMasterFilter n3Filter(List<String> periods) {
        List<String> items = Lists.newArrayList("c7", "k1", "k2", "c6", "d3", "d2");
        return new WorseMasterFilter(periods, items, periods.size(), 1000);
    }

    public static WorseMasterFilter ssqFilter(List<String> periods) {
        List<String> items = Lists.newArrayList("r3", "r12", "r20", "r25", "rk3", "rk6");
        return new WorseMasterFilter(periods, items, periods.size(), 900);
    }

    public static WorseMasterFilter dltFilter(List<String> periods) {
        List<String> items = Lists.newArrayList("r3", "r10", "r20", "rk3", "rk6");
        return new WorseMasterFilter(periods, items, periods.size(), 900);
    }

    public static WorseMasterFilter qlcFilter(List<String> periods) {
        ArrayList<String> items = Lists.newArrayList("r3", "r12", "r18", "r22", "k3", "k6");
        return new WorseMasterFilter(periods, items, periods.size(), 900);
    }

}
