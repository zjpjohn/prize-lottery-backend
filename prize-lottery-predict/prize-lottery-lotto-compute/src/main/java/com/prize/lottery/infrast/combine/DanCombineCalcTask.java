package com.prize.lottery.infrast.combine;

import com.cloud.arch.utils.JsonUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DanCombineCalcTask implements Callable<Map<String, List<Pair<String, Long>>>> {

    //任务名称
    private String       taskName;
    //数据源
    private List<String> sources;
    //分段大小
    private Integer      segSize;
    //组选数量
    private Integer      comNum;
    //聚合后门槛参数
    private Integer      throttle;


    public DanCombineCalcTask(String taskName,
                              List<String> sources,
                              Integer segSize,
                              Integer comNum,
                              Integer throttle) {
        this.taskName = taskName;
        this.sources  = sources;
        this.segSize  = segSize;
        this.comNum   = comNum;
        this.throttle = throttle;
    }

    public List<String> getSources() {
        return sources;
    }

    public Integer getSegSize() {
        return segSize;
    }

    public Integer getComNum() {
        return comNum;
    }

    public Integer getThrottle() {
        return throttle;
    }

    public String getTaskName() {
        return taskName;
    }

    /**
     * 胆码组合计算
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Map<String, List<Pair<String, Long>>> call() throws Exception {
        List<List<String>> results = Lists.newArrayList();
        if (sources.size() > segSize) {
            Integer sourceLength = sources.size();
            Integer sourceSegments = (sourceLength % segSize == 0) ?
                                     (sourceLength / segSize) :
                                     (sourceLength / segSize + 1);
            Integer startSourceIdx = 0;
            Integer endSourceIdx   = 0;
            for (int i = 1; i <= sourceSegments; i++) {
                startSourceIdx = (i - 1) * segSize;
                if (i == sourceSegments) {
                    endSourceIdx = sourceLength;
                } else {
                    endSourceIdx = i * segSize;
                }
                List<String> segSources = Lists.newArrayList();
                for (int j = startSourceIdx; j < endSourceIdx; j++) {
                    segSources.add(sources.get(j));
                }
                int combine = (int) CombinationUtils.getCombine(segSources.size(), comNum);
                for (int k = 0; k < combine; k++) {
                    List<String> values = CombinationUtils.getCombineValue(segSources, comNum, k);
                    List<String> list = values.stream().flatMap(value -> {
                        List<String> result = Splitter.on(Pattern.compile("\\s+"))
                                                      .trimResults()
                                                      .omitEmptyStrings()
                                                      .splitToList(value);
                        return result.stream();
                    }).distinct().collect(Collectors.toList());
                    if (list.size() <= throttle) {
                        results.add(list);
                    }
                }
            }
        } else {
            int combine = (int) CombinationUtils.getCombine(sources.size(), comNum);
            for (int k = 0; k < combine; k++) {
                List<String> values = CombinationUtils.getCombineValue(sources, comNum, k);
                List<String> list = values.stream().flatMap(value -> {
                    List<String> result = Splitter.on(Pattern.compile("\\s+"))
                                                  .trimResults()
                                                  .omitEmptyStrings()
                                                  .splitToList(value);
                    return result.stream();
                }).distinct().collect(Collectors.toList());
                if (list.size() <= throttle) {
                    results.add(list);
                }
            }
        }
        List<Pair<String, Long>> pairs = results.stream()
                                                .flatMap(Collection::stream)
                                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                                .entrySet()
                                                .stream()
                                                .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                                                .map(item -> Pair.of(item.getKey(), item.getValue()))
                                                .collect(Collectors.toList());
        System.out.println(taskName + "结果:" + JsonUtils.toJson(pairs));
        Map<String, List<Pair<String, Long>>> map = Maps.newHashMap();
        map.put(taskName, pairs);
        return map;
    }
}
