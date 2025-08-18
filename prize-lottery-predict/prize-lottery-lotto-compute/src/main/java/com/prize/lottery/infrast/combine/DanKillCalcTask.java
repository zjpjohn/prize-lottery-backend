package com.prize.lottery.infrast.combine;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.Callable;

public class DanKillCalcTask implements Callable<List<String>> {

    private List<List<String>> sources;
    private Integer            comNum;

    public DanKillCalcTask(List<List<String>> sources,
                           Integer comNum) {
        this.sources = sources;
        this.comNum  = comNum;
    }

    @Override
    public List<String> call() throws Exception {
        int          combine = (int) CombinationUtils.getCombine(sources.size(), comNum);
        List<String> results = Lists.newArrayList();
        for (int j = 0; j < combine; j++) {
            List<List<String>> values  = CombinationUtils.getCombineValue(sources, comNum, j);
            List<String>       collect = Lists.newArrayList();
            collect.addAll(values.get(0));
            for (int k = 1; k < values.size(); k++) {
                collect.retainAll(values.get(k));
            }
            if (collect.size() > 0) {
                results.addAll(collect);
                System.out.println(comNum + "组选相同的数据量：" + collect.size());
            }
        }
        return results;
    }
}
