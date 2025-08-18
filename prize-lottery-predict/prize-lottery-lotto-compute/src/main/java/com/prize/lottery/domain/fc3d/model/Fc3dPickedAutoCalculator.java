package com.prize.lottery.domain.fc3d.model;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.collect.Maps;
import com.prize.lottery.domain.value.CensusOperator;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.po.fc3d.Fc3dLottoCensusPo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class Fc3dPickedAutoCalculator {

    private CensusOperator kill1;
    private CensusOperator kill2;

    private TreeMap<String, Double> kWeight;
    private List<String>            danList;
    private List<String>            killList;

    public Fc3dPickedAutoCalculator(List<Fc3dLottoCensusPo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Map<Fc3dChannel, List<Fc3dLottoCensusPo>> channelListMap =
            CollectionUtils.groupBy(list, Fc3dLottoCensusPo::getChannel);
        kill1 = CensusOperator.fromChannelCensus(channelListMap.get(Fc3dChannel.KILL1));
        kill2 = CensusOperator.fromChannelCensus(channelListMap.get(Fc3dChannel.KILL2));
    }

    /**
     * 计算胆码和杀码
     */
    public void calculate() {
        if (kill1 == null || kill2 == null) {
            return;
        }
        Map<String, Double> kill1Weight = this.calcOperatorWeight(kill1);
        Map<String, Double> kill2Weight = this.calcOperatorWeight(kill2);
        Map<String, Double> killWeight  = Maps.newHashMap();
        Set<String>         keys        = kill1Weight.keySet();
        for (String key : keys) {
            killWeight.put(key, this.scaleValue(kill1Weight.get(key) * 5.5 + kill2Weight.get(key) * 4.3));
        }
        this.kWeight = Maps.newTreeMap(Comparator.comparing(killWeight::get));
        kWeight.putAll(killWeight);
        danList  = kWeight.keySet().stream().limit(2).collect(Collectors.toList());
        killList = kWeight.descendingKeySet().stream().limit(3).collect(Collectors.toList());
    }

    private Map<String, Double> calcOperatorWeight(CensusOperator operator) {
        Map<String, Double> level10  = operator.getLevel10Weight();
        Map<String, Double> level20  = operator.getLevel20Weight();
        Map<String, Double> level50  = operator.getLevel50Weight();
        Map<String, Double> level100 = operator.getLevel100Weight();
        Map<String, Double> level150 = operator.getLevel150Weight();
        Set<String>         keys     = level10.keySet();
        Map<String, Double> total    = Maps.newHashMap();
        for (String key : keys) {
            double weight = level10.get(key) * 3.3
                            + level20.get(key) * 3.1
                            + level50.get(key) * 2.5
                            + level100.get(key) * 1.7
                            + level150.get(key) * 0.5;
            total.put(key, this.scaleValue(weight));
        }
        return total;
    }

    private double scaleValue(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
