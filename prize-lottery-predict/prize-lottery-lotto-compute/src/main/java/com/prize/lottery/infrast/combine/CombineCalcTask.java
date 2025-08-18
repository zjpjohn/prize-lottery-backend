package com.prize.lottery.infrast.combine;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class CombineCalcTask implements Callable<List<String>> {

    private List<String> sources;
    private List<String> includes;
    private Integer      comNum;

    public CombineCalcTask(List<String> sources,
                           List<String> includes,
                           Integer comNum) {
        this.sources  = sources;
        this.includes = includes;
        this.comNum   = comNum;
    }

    /**
     * 计算任务
     *
     * @return computed result
     *
     * @throws Exception if unable to compute a result
     */
    @Override
    public List<String> call() throws Exception {
        int          combine    = (int) CombinationUtils.getCombine(sources.size(), comNum);
        List<String> combineAll = Lists.newArrayList();
        for (int k = 0; k < combine; k++) {
            List<String> values = CombinationUtils.getCombineValue(sources, comNum, k);
            List<String> list   = values.stream().distinct().sorted().collect(Collectors.toList());
            if (list.size() == 6
                    && Integer.valueOf(list.get(0)) < 19
                    && Integer.valueOf(list.get(list.size() - 1)) > 15) {
                Boolean mark = true;
                if (includes != null) {
                    mark = list.containsAll(includes);
                }
                if (mark) {
                    String join = Joiner.on(",").join(list);
                    combineAll.add(join);
                }
            }
        }
        //有满足条件的数据
        System.out.println("数据源总数:" + sources.size() + "满足条件的数据条数:" + combineAll.size());
        return combineAll;
    }
}
