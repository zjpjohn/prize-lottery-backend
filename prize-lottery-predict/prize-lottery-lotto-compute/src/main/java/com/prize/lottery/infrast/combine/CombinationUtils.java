package com.prize.lottery.infrast.combine;

import com.cloud.arch.utils.JsonUtils;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CombinationUtils {

    private static final Integer DEFAULT_THREAD_SIZE = 20;

    private static final ExecutorService executors = Executors.newFixedThreadPool(DEFAULT_THREAD_SIZE);

    /**
     * 计算组合数对应的值
     *
     * @param source
     * @param m
     * @param x
     * @return
     */
    public static <T> List<T> getCombineValue(List<T> source, int m, int x) {
        int     n     = source.size();
        List<T> temp  = Lists.newArrayList();
        int     start = 0;
        while (m > 0) {
            if (m == 1) {
                //最后一个数
                temp.add(source.get(start + x));
                break;
            }
            for (int i = 0; i <= (n - m); i++) {
                int cmb = (int) getCombine(n - 1 - i, m - 1);
                if (x <= cmb - 1) {
                    temp.add(source.get(start + i));
                    start = start + (i + 1);
                    n     = n - (i + 1);
                    m--;
                    break;
                } else {
                    x = x - cmb;
                }
            }
        }
        return temp;
    }

    public static long getCombine(Integer n, Integer m) {
        if (n < 0 || m < 0) {
            throw new IllegalArgumentException("n,m必须大于0");
        }
        if (n == 0 || m == 0) {
            return 1;
        }
        if (n < m) {
            return 0;
        }
        if (m > n / 2.0) {
            m = n - m;
        }
        double result = 0.0;
        for (int i = n; i >= (n - m + 1); i--) {
            result += Math.log(i);
        }
        for (int i = m; i >= 1; i--) {
            result -= Math.log(i);
        }
        result = Math.exp(result);
        return Math.round(result);
    }

    /**
     * 球两个集合的差集
     *
     * @param source
     * @param target
     * @return
     */
    public static List<String> distinctSet(List<String> source, List<String> target) {
        List<String> result = Lists.newArrayList();
        for (String s : source) {
            if (!target.contains(s)) {
                result.add(s);
            }
        }
        for (String s : target) {
            if (!source.contains(s)) {
                result.add(s);
            }
        }

        return result;
    }

    /**
     * 求两个集合的差集
     *
     * @param source
     * @param target
     * @return
     */
    public static List<String> differences(List<String> source, List<String> target) {
        Sets.SetView<String> result = Sets.difference(Sets.newHashSet(source), Sets.newHashSet(target));
        return result.stream().sorted().collect(Collectors.toList());
    }


    public static List<String> commonStr(List<String> source, List<String> target) {
        List<String> result = Lists.newArrayList();
        for (String s : source) {
            if (target.contains(s)) {
                result.add(s);
            }
        }
        return result;
    }


    public static void red25Rec(List<List<String>> source, Integer comNum, Integer minCommon, Integer maxCommon)
            throws Exception {
        int                              combine = (int) getCombine(source.size(), comNum);
        Map<Integer, List<List<String>>> results = Maps.newHashMap();
        for (int i = 0; i < combine; i++) {
            List<List<String>> value = getCombineValue(source, comNum, i);
            Map<String, Long> map = value.stream()
                                         .flatMap(item -> item.stream())
                                         .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            Integer count = 0;
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                if (entry.getValue() - comNum == 0) {
                    count++;
                }
            }
            if (count >= minCommon && count <= maxCommon) {
                List<String> list = map.entrySet()
                                       .stream()
                                       .filter(item -> item.getValue() - comNum == 0)
                                       .map(entry -> entry.getKey())
                                       .sorted()
                                       .collect(Collectors.toList());
                results.computeIfAbsent(list.size(), key -> Lists.newArrayList()).add(list);
            }
        }
        for (Map.Entry<Integer, List<List<String>>> entry : results.entrySet()) {
            System.out.println("长度为:" + entry.getKey() + "的数据集长度:" + entry.getValue().size());
            List<List<String>> value = entry.getValue();
            if (value.size() < 10) {
                for (List<String> list : value) {
                    System.out.println(list);
                }
            }
        }

    }

    /**
     * 组合求出相同数据
     *
     * @param sources
     * @param comNum
     * @param common
     */
    public static List<List<String>> commonCombine(List<String> sources, Integer comNum, Integer common) {
        Integer            size        = sources.size();
        Integer            redSegSize  = 40;
        Integer            redSegments = (size % redSegSize == 0) ? (size / redSegSize) : (size / redSegSize + 1);
        Integer            startIdx    = 0;
        List<List<String>> result      = Lists.newArrayList();
        for (int i = 1; i <= redSegments; i++) {
            startIdx = (i - 1) * redSegSize;
            List<String> collect = sources.stream().skip(startIdx).limit(redSegSize).collect(Collectors.toList());
            int          combine = (int) getCombine(collect.size(), comNum);
            for (int j = 0; j < combine; j++) {
                List<String> value = getCombineValue(collect, comNum, j);
                Map<String, Long> map = value.stream().flatMap(item -> {
                    List<String> list = Splitter.on(Pattern.compile(","))
                                                .trimResults()
                                                .omitEmptyStrings()
                                                .splitToList(item);
                    return list.stream();
                }).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                Integer count = 0;
                for (Map.Entry<String, Long> entry : map.entrySet()) {
                    if (entry.getValue() - comNum == 0) {
                        count++;
                    }
                }
                if (count >= common) {
                    List<String> list = map.entrySet()
                                           .stream()
                                           .filter(entry -> entry.getValue() - comNum == 0)
                                           .map(entry -> entry.getKey())
                                           .sorted()
                                           .collect(Collectors.toList());
                    if (Integer.valueOf(list.get(0)) < 19 && Integer.valueOf(list.get(list.size() - 1)) > 15) {
                        result.add(list);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 红球20码组合计算
     *
     * @param source
     * @param comNum
     * @param minThrottle
     * @param maxThrottle
     * @param common
     * @return
     */
    public static Map<Integer, Set<String>> red20Combine(List<List<String>> source,
                                                         Integer comNum,
                                                         Integer minThrottle,
                                                         Integer maxThrottle,
                                                         Integer common) {
        long                      combine             = getCombine(source.size(), comNum);
        int                       availableProcessors = Runtime.getRuntime().availableProcessors();
        ForkJoinPool              forkJoinPool        = new ForkJoinPool(availableProcessors);
        Red20CombineTask          task                = new Red20CombineTask(source, 0L, combine, comNum, common);
        Map<Integer, Set<String>> results             = forkJoinPool.invoke(task);
        return results;
    }

    /**
     * 篮球组合计算
     *
     * @param sources
     * @param comNum
     * @return
     */
    public static List<List<String>> blueCombine(List<List<String>> sources, Integer comNum) {
        int                combine = (int) getCombine(sources.size(), comNum);
        List<List<String>> results = Lists.newArrayList();
        for (int i = 0; i < combine; i++) {
            List<List<String>> values = getCombineValue(sources, comNum, i);
            List<String> list = values.stream()
                                      .flatMap(item -> item.stream())
                                      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                      .entrySet()
                                      .stream()
                                      .filter(entry -> entry.getValue() - comNum == 0)
                                      .map(entry -> entry.getKey())
                                      .sorted()
                                      .collect(Collectors.toList());
            if (list.size() > 0) {
                results.add(list);
            }
        }
        System.out.println("组合数据集大小:" + results.size());
        return results;
    }

    /**
     * 胆码组合计算
     *
     * @param sources
     * @param comNum
     * @param minThrottle
     * @param maxThrottle
     * @param common
     */
    public static Map<Integer, Set<String>> danCombine(List<List<String>> sources,
                                                       Integer comNum,
                                                       Integer minThrottle,
                                                       Integer maxThrottle,
                                                       Integer common,
                                                       Integer maxCommon,
                                                       List<String> dans,
                                                       List<String> excludes) {
        int                       combine = (int) getCombine(sources.size(), comNum);
        Map<Integer, Set<String>> results = Maps.newHashMap();
        for (int i = 0; i < combine; i++) {
            List<List<String>> values = getCombineValue(sources, comNum, i);
            Map<String, Long> collect = values.stream()
                                              .flatMap(item -> item.stream())
                                              .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            Integer count = 0;
            for (Map.Entry<String, Long> entry : collect.entrySet()) {
                if (entry.getValue() - comNum == 0) {
                    count++;
                }
            }
            if (count >= common
                    && count <= maxCommon
                    && collect.size() >= minThrottle
                    && collect.size() <= maxThrottle) {
                List<String> list = collect.entrySet()
                                           .stream()
                                           .filter(item -> item.getValue() - comNum == 0)
                                           .map(item -> item.getKey())
                                           .sorted()
                                           .collect(Collectors.toList());
                boolean mark = true;
                if (dans != null && dans.size() > 0 && !list.containsAll(dans)) {
                    mark = false;
                }
                if (excludes != null && excludes.size() > 0 && excludes.containsAll(list)) {
                    mark = false;
                }
                if (mark) {
                    String join = Joiner.on(",").join(list);
                    results.computeIfAbsent(list.size(), key -> Sets.newTreeSet()).add(join);
                }
            }
        }
        List<Integer> keys = results.keySet().stream().sorted().collect(Collectors.toList());
        for (Integer key : keys) {
            List<String> value = results.get(key).stream().distinct().collect(Collectors.toList());
            System.out.println("相同数据长度:" + key + ",数据长度:" + value.size());
        }
        System.out.println("\n+++++++++++++++++++++++++++++++\n");
        return results;
    }

    /**
     * 最长最短数据集合
     *
     * @param sources
     * @param comNum
     * @return
     */
    public static List<String> killCombine(List<List<String>> sources, Integer comNum) throws Exception {
        int                   combine  = (int) getCombine(sources.size(), comNum);
        int                   segSize  = 10;
        int                   segments = (combine % segSize == 0) ? (combine / segSize) : (combine / segSize + 1);
        List<ItemCombineTask> tasks    = Lists.newArrayList();
        for (int i = 1; i <= segments; i++) {
            int start = (i - 1) * segSize;
            int end   = 0;
            if (i == segments) {
                end = combine;
            } else {
                end = i * segSize;
            }
            tasks.add(new ItemCombineTask(i + "-task", sources, start, end, comNum));
        }
        List<Future<ItemCombine>> futures      = executors.invokeAll(tasks);
        ItemCombine               finalCombine = new ItemCombine(0, 35);
        for (Future<ItemCombine> future : futures) {
            ItemCombine item = future.get();
            item.getMaxSources().forEach(finalCombine::calcCombine);
            item.getMinSources().forEach(finalCombine::calcCombine);
        }
        System.out.println("最终最长:" + finalCombine.getMax() + ",最终最长数据:" + finalCombine.maxContainer.size());
        System.out.println("最终最短:" + finalCombine.getMin() + ",最终最短数据:" + finalCombine.minContainer.size());
        System.out.println("========最长的组合==========");
        System.out.println("最长组合个数:" + finalCombine.getMaxSources().size());
        for (List<List<String>> source : finalCombine.getMaxSources()) {
            System.out.println(JsonUtils.toJson(source));
        }
        System.out.println("========最短的组合==========");
        for (List<List<String>> source : finalCombine.getMinSources()) {
            System.out.println(JsonUtils.toJson(source));
        }
        return null;
    }


    /**
     * 红球胆码组合分析
     *
     * @param sources     数据源
     * @param comNum      组合数
     * @param minThrottle 门槛
     * @param maxThrottle
     */
    public static List<Pair<Integer, List<Pair<String, Long>>>> redDanCombine(List<String> sources,
                                                                              Integer comNum,
                                                                              Integer minThrottle,
                                                                              Integer maxThrottle,
                                                                              Integer common) {
        int                        combine = (int) getCombine(sources.size(), comNum);
        Map<Integer, List<String>> results = Maps.newHashMap();
        for (int i = 0; i < combine; i++) {
            List<String> values = getCombineValue(sources, comNum, i);
            Map<String, Long> collect = values.stream().flatMap(item -> {
                List<String> list = Splitter.on(Pattern.compile("\\s+"))
                                            .trimResults()
                                            .omitEmptyStrings()
                                            .splitToList(item);
                return list.stream();
            }).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            Integer count = 0;
            for (Map.Entry<String, Long> entry : collect.entrySet()) {
                if (entry.getValue() - comNum == 0) {
                    count++;
                }
            }
            List<String> result = values.stream().flatMap(item -> {
                List<String> list = Splitter.on(Pattern.compile("\\s+"))
                                            .trimResults()
                                            .omitEmptyStrings()
                                            .splitToList(item);
                return list.stream();
            }).distinct().sorted().collect(Collectors.toList());
            if (count >= common && result.size() >= minThrottle && result.size() <= maxThrottle) {
                List<String> list = collect.entrySet()
                                           .stream()
                                           .filter(item -> item.getValue() - comNum == 0)
                                           .map(item -> item.getKey())
                                           .sorted()
                                           .collect(Collectors.toList());
                String join = Joiner.on(",").join(list);
                results.computeIfAbsent(count, key -> Lists.newArrayList()).add(join);
            }
        }
        List<Pair<Integer, List<Pair<String, Long>>>> list = Lists.newArrayList();
        for (Map.Entry<Integer, List<String>> entry : results.entrySet()) {
            List<Pair<String, Long>> pairs = entry.getValue()
                                                  .stream()
                                                  .flatMap(item -> {
                                                      List<String> split = Splitter.on(Pattern.compile(","))
                                                                                   .trimResults()
                                                                                   .omitEmptyStrings()
                                                                                   .splitToList(item);
                                                      return split.stream();
                                                  })
                                                  .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                                  .entrySet()
                                                  .stream()
                                                  .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                                                  .map(item -> Pair.of(item.getKey(), item.getValue()))
                                                  .collect(Collectors.toList());
            list.add(Pair.of(entry.getKey(), pairs));
        }
        return list;
    }

    /**
     * 组合求出数据之间的差集
     *
     * @param sources
     * @return
     */
    public static List<String> distinctCombine(List<String> sources) {
        if (sources.size() < 2) {
            return null;
        }
        int                combine = (int) getCombine(sources.size(), 2);
        List<List<String>> result  = Lists.newArrayList();
        for (int i = 0; i < combine; i++) {
            List<String> values   = getCombineValue(sources, 2, i);
            String       source   = values.get(0);
            List<String> src      = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(source);
            String       target   = values.get(1);
            List<String> tgt      = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(target);
            List<String> distinct = distinctSet(src, tgt);
            result.add(distinct);
        }
        List<String> list = result.stream()
                                  .flatMap(item -> item.stream())
                                  .distinct()
                                  .sorted()
                                  .collect(Collectors.toList());
        return list;
    }

    public static List<Pair<String, Long>> commonCombine(List<String> sources) {
        int                combine = (int) getCombine(sources.size(), 2);
        List<List<String>> result  = Lists.newArrayList();
        for (int i = 0; i < combine; i++) {
            List<String> values   = getCombineValue(sources, 2, i);
            String       source   = values.get(0);
            List<String> src      = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(source);
            String       target   = values.get(1);
            List<String> tgt      = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(target);
            List<String> distinct = commonStr(src, tgt);
            result.add(distinct);
        }
        List<Pair<String, Long>> pairs = result.stream()
                                               .flatMap(item -> item.stream())
                                               .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                               .entrySet()
                                               .stream()
                                               .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                                               .map(item -> Pair.of(item.getKey(), item.getValue()))
                                               .collect(Collectors.toList());
        return pairs;
    }
}

