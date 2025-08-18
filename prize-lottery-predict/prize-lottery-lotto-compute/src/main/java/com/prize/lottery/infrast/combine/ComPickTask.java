package com.prize.lottery.infrast.combine;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ComPickTask implements Callable<List<String>> {

    private List<List<String>> source;
    private Integer            num;
    private long               start;
    private long               end;

    public ComPickTask(List<List<String>> source,
                       Integer num,
                       long start,
                       long end) {
        this.source = source;
        this.num    = num;
        this.start  = start;
        this.end    = end;
    }

    @Override
    public List<String> call() throws Exception {
        List<String> result = Lists.newArrayList();
        for (long i = start; i < end; i++) {
            List<List<String>> value = CombinationUtils.getCombineValue(source, num, (int) i);
            List<String> list = value.stream()
                    .flatMap(item -> item.stream())
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() - num == 0)
                    .map(entry -> entry.getKey())
                    .collect(Collectors.toList());
            if (list.size() == 3) {
                result.add(Joiner.on(" ").join(list));
            }
        }
        return result;
    }
}
