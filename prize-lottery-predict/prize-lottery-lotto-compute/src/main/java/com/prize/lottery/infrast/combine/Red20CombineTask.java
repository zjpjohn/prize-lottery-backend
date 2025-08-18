package com.prize.lottery.infrast.combine;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Red20CombineTask extends RecursiveTask<Map<Integer, Set<String>>> {

    private static final long serialVersionUID = -3404851825659023074L;

    private static final long THRESHOLD = 100000;

    private List<List<String>> sources;
    private Long               startIdx;
    private Long               endIdx;
    private Integer            comNum;
    private Integer            common;

    public Red20CombineTask(List<List<String>> sources,
                            Long startIdx,
                            Long endIdx,
                            Integer comNum,
                            Integer common) {
        this.sources  = sources;
        this.startIdx = startIdx;
        this.endIdx   = endIdx;
        this.comNum   = comNum;
        this.common   = common;
    }

    public List<List<String>> getSources() {
        return sources;
    }

    public Long getStartIdx() {
        return startIdx;
    }

    public Long getEndIdx() {
        return endIdx;
    }

    public Integer getComNum() {
        return comNum;
    }

    public Integer getCommon() {
        return common;
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Map<Integer, Set<String>> compute() {
        if (endIdx - startIdx < THRESHOLD) {
            Map<Integer, Set<String>> results = Maps.newHashMap();
            for (long i = startIdx; i < endIdx; i++) {
                List<List<String>> value = CombinationUtils.getCombineValue(sources, comNum, (int) i);
                Map<String, Long> map = value.stream()
                        .flatMap(item -> item.stream())
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                Integer count = 0;
                for (Map.Entry<String, Long> entry : map.entrySet()) {
                    if (entry.getValue() - comNum == 0) {
                        count++;
                    }
                }
                if (count >= common && map.size() == 33) {
                    List<String> list = map.entrySet()
                            .stream()
                            .filter(item -> item.getValue() - comNum == 0)
                            .map(item -> item.getKey())
                            .sorted()
                            .collect(Collectors.toList());
                    String join = Joiner.on(",").join(list);
                    results.computeIfAbsent(list.size(), key -> Sets.newTreeSet()).add(join);
                }
            }
            return results;
        }
        long             middle = (endIdx + startIdx) / 2;
        Red20CombineTask task1  = new Red20CombineTask(sources, startIdx, middle, comNum, common);
        Red20CombineTask task2  = new Red20CombineTask(sources, middle, endIdx, comNum, common);
        invokeAll(task1, task2);
        Map<Integer, Set<String>> result1 = task1.join();
        Map<Integer, Set<String>> result2 = task2.join();
        Map<Integer, Set<String>> result  = Maps.newHashMap();
        for (Map.Entry<Integer, Set<String>> entry : result1.entrySet()) {
            result.computeIfAbsent(entry.getKey(), key -> Sets.newTreeSet()).addAll(entry.getValue());
        }
        for (Map.Entry<Integer, Set<String>> entry : result2.entrySet()) {
            result.computeIfAbsent(entry.getKey(), key -> Sets.newTreeSet()).addAll(entry.getValue());
        }
        return result;
    }

}
