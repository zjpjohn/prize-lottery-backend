package com.prize.lottery.infrast.combine;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class ComKillTask implements Callable<List<String>> {

    private List<List<String>> source;
    private Integer            num;
    private long               start;
    private long               end;

    public ComKillTask(List<List<String>> source,
                       Integer num,
                       long start,
                       long end) {
        this.source = source;
        this.num    = num;
        this.start  = start;
        this.end    = end;
    }

    /**
     * Computes a result, or throws an error if unable to do so.
     *
     * @return computed result
     *
     * @throws Exception if unable to compute a result
     */
    @Override
    public List<String> call() throws Exception {
        List<String> result = Lists.newArrayList();
        for (long i = start; i < end; i++) {
            List<List<String>> value = CombinationUtils.getCombineValue(source, num, (int) i);
            List<String> collect = value.stream()
                    .flatMap(item -> item.stream())
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            if (collect.size() == 7) {
                String join = Joiner.on(" ").join(collect);
                result.add(join);
            }
        }
        return result.stream().distinct().collect(Collectors.toList());
    }
}
