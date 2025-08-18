package com.prize.lottery.infrast.combine;

import lombok.Data;

import java.util.List;
import java.util.concurrent.Callable;

@Data
public class ItemCombineTask implements Callable<ItemCombine> {

    //任务名称
    private String             taskName;
    //数据源
    private List<List<String>> sources;
    //分段起始
    private Integer            start;
    //分段结束
    private Integer            end;
    //组合大小
    private Integer            combine;

    public ItemCombineTask(String taskName,
                           List<List<String>> sources,
                           Integer start,
                           Integer end,
                           Integer combine) {
        this.taskName = taskName;
        this.sources  = sources;
        this.start    = start;
        this.end      = end;
        this.combine  = combine;
    }

    /**
     * 结果计算
     *
     * @return computed result
     *
     * @throws Exception if unable to compute a result
     */
    @Override
    public ItemCombine call() throws Exception {
        ItemCombine result = new ItemCombine(0, 35);
        for (int i = start; i < end; i++) {
            List<List<String>> values = CombinationUtils.getCombineValue(sources, combine, i);

            result.calcCombine(values);
        }
        return result;
    }
}
