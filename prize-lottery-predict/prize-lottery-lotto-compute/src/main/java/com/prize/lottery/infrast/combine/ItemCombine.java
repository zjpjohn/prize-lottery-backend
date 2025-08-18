package com.prize.lottery.infrast.combine;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ItemCombine {
    //最大长度
    Integer     max;
    //最小长度
    Integer     min;
    //最大集合
    Set<String> maxContainer = Sets.newHashSet();

    Set<List<List<String>>> maxSources = Sets.newHashSet();

    //最小集合
    Set<String> minContainer = Sets.newHashSet();

    Set<List<List<String>>> minSources = Sets.newHashSet();

    public ItemCombine(int max, Integer min) {
        this.max = max;
        this.min = min;
    }

    public void calcCombine(List<List<String>> source) {
        List<String> data = source.stream()
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        //最大值
        if (data.size() > max) {
            max = data.size();
            maxContainer.clear();
            maxContainer.add(Joiner.on(",").join(data));
            maxSources.clear();
            maxSources.add(source);
            return;
        }
        if (data.size() == max) {
            maxContainer.add(Joiner.on(",").join(data));
            maxSources.add(source);
            return;
        }
        //最小值
        if (data.size() < min) {
            min = data.size();
            minContainer.clear();
            minContainer.add(Joiner.on(",").join(data));
            minSources.clear();
            minSources.add(source);
            return;
        }
        if (data.size() == min) {
            minContainer.add(Joiner.on(",").join(data));
            minSources.add(source);
        }
    }
}
